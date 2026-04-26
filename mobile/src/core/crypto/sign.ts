/**
 * TRON transaction signing.
 *
 * Input  `unsignedTxHex` is the protobuf-serialized `raw_data` (a.k.a.
 * `raw_data_hex` in TronWeb).  The TRON `txID` is `sha256(raw_data)`.
 *
 * Signing: deterministic ECDSA over secp256k1 (RFC 6979) — byte-identical
 * output to TronWeb's `tronWeb.trx.sign(tx)` for the same private key.
 *
 * Output is the 65-byte recoverable signature `r(32) || s(32) || v(1)` as hex
 * (130 chars, no 0x prefix).  This is what `POST /transfer/broadcast`
 * expects in `signed_tx`; the backend re-attaches it to the original
 * transaction it built, then broadcasts via TronGrid.
 *
 * SECURITY: never logs `unsignedTxHex` or `privateKeyHex`; never returns
 * intermediate values.  The wrapper is intentionally tiny so reviewers can
 * audit it in one read.
 */
import { secp256k1 } from '@noble/curves/secp256k1';
import { sha256 } from '@noble/hashes/sha256';
import { bytesToHex, hexToBytes } from '@noble/hashes/utils';

const SIG_LEN = 65;

export function txIdFromRawData(unsignedTxHex: string): Uint8Array {
  return sha256(hexToBytes(stripHex(unsignedTxHex)));
}

export function signTransaction(unsignedTxHex: string, privateKeyHex: string): string {
  const rawData = hexToBytes(stripHex(unsignedTxHex));
  const pk = hexToBytes(stripHex(privateKeyHex));
  if (pk.length !== 32) {
    throw new Error('Private key must be 32 bytes');
  }
  const txId = sha256(rawData);
  const sig = secp256k1.sign(txId, pk);
  const compact = sig.toCompactRawBytes();
  const out = new Uint8Array(SIG_LEN);
  out.set(compact, 0);
  out[64] = sig.recovery;
  return bytesToHex(out);
}

function stripHex(s: string): string {
  return s.startsWith('0x') || s.startsWith('0X') ? s.slice(2) : s;
}
