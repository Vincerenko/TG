import { describe, expect, it } from 'vitest';
import { secp256k1 } from '@noble/curves/secp256k1';
import { hexToBytes } from '@noble/hashes/utils';
import { signTransaction, txIdFromRawData } from './sign.js';
import { SIGN_VECTOR } from './__fixtures__/tron.js';

describe('sign.txIdFromRawData', () => {
  it('returns sha256(raw_data) — TRON txID convention', () => {
    const txId = txIdFromRawData(SIGN_VECTOR.raw_data_hex);
    expect(Buffer.from(txId).toString('hex')).toBe(SIGN_VECTOR.txid_hex);
  });

  it('accepts 0x-prefixed input', () => {
    const a = txIdFromRawData(SIGN_VECTOR.raw_data_hex);
    const b = txIdFromRawData('0x' + SIGN_VECTOR.raw_data_hex);
    expect(Buffer.from(a).toString('hex')).toBe(Buffer.from(b).toString('hex'));
  });

  it('accepts 0X-prefixed input', () => {
    const a = txIdFromRawData(SIGN_VECTOR.raw_data_hex);
    const b = txIdFromRawData('0X' + SIGN_VECTOR.raw_data_hex);
    expect(Buffer.from(a).toString('hex')).toBe(Buffer.from(b).toString('hex'));
  });
});

describe('sign.signTransaction', () => {
  it('produces deterministic 65-byte recoverable signature (RFC 6979)', () => {
    const sig = signTransaction(SIGN_VECTOR.raw_data_hex, SIGN_VECTOR.privateKey_hex);
    expect(sig).toBe(SIGN_VECTOR.signature_hex);
    expect(sig).toHaveLength(130);
    expect(/^[0-9a-f]+$/.test(sig)).toBe(true);
  });

  it('is deterministic across repeated calls', () => {
    const a = signTransaction(SIGN_VECTOR.raw_data_hex, SIGN_VECTOR.privateKey_hex);
    const b = signTransaction(SIGN_VECTOR.raw_data_hex, SIGN_VECTOR.privateKey_hex);
    expect(a).toBe(b);
  });

  it('signature verifies against the secp256k1 pubkey + txID', () => {
    const sigHex = signTransaction(SIGN_VECTOR.raw_data_hex, SIGN_VECTOR.privateKey_hex);
    const sigBytes = hexToBytes(sigHex);
    const compact = sigBytes.subarray(0, 64);
    const recovery = sigBytes[64];
    const txId = txIdFromRawData(SIGN_VECTOR.raw_data_hex);
    const sig = secp256k1.Signature.fromCompact(compact).addRecoveryBit(recovery);
    const pub = sig.recoverPublicKey(txId).toRawBytes(false);
    const expectedPub = secp256k1.getPublicKey(hexToBytes(SIGN_VECTOR.privateKey_hex), false);
    expect(Buffer.from(pub).toString('hex')).toBe(Buffer.from(expectedPub).toString('hex'));
  });

  it('accepts 0x-prefixed private key', () => {
    const sig = signTransaction(SIGN_VECTOR.raw_data_hex, '0x' + SIGN_VECTOR.privateKey_hex);
    expect(sig).toBe(SIGN_VECTOR.signature_hex);
  });

  it('throws for wrong-length private key', () => {
    expect(() => signTransaction(SIGN_VECTOR.raw_data_hex, 'deadbeef')).toThrow(/32 bytes/);
  });
});
