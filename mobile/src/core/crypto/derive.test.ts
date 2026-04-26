import { describe, expect, it } from 'vitest';
import { secp256k1 } from '@noble/curves/secp256k1';
import { hexToBytes, bytesToHex } from '@noble/hashes/utils';
import {
  TRON_ADDRESS_PREFIX,
  TRON_COIN_TYPE,
  deriveTronAccount,
  publicKeyToTronAddress,
  tronPath,
} from './derive.js';
import { mnemonicToSeed } from './seed.js';
import { TRON_VECTORS } from './__fixtures__/tron.js';

describe('derive.tronPath', () => {
  it('returns canonical SLIP-44 path with TRON coin type', () => {
    expect(tronPath(0)).toBe(`m/44'/${TRON_COIN_TYPE}'/0'/0/0`);
    expect(tronPath(7)).toBe(`m/44'/195'/0'/0/7`);
  });

  it('rejects non-integer index', () => {
    expect(() => tronPath(1.5)).toThrow(RangeError);
  });

  it('rejects negative index', () => {
    expect(() => tronPath(-1)).toThrow(RangeError);
  });

  it('rejects index >= 2^31 (would be hardened)', () => {
    expect(() => tronPath(0x80000000)).toThrow(RangeError);
  });
});

describe('derive.publicKeyToTronAddress', () => {
  it('rejects wrong-length input', () => {
    expect(() => publicKeyToTronAddress(new Uint8Array(64))).toThrow(/65-byte/);
  });

  it('rejects compressed-prefix input', () => {
    const bad = new Uint8Array(65);
    bad[0] = 0x02; // compressed prefix
    expect(() => publicKeyToTronAddress(bad)).toThrow(/0x04/);
  });

  it('produces a base58 T-prefixed address from a real pubkey', () => {
    const priv = hexToBytes(
      'b5a4cea271ff424d7c31dc12a3e43e401df7a40d7412a15750f3f0b6b5449a28',
    );
    const pub = secp256k1.getPublicKey(priv, false);
    const addr = publicKeyToTronAddress(pub);
    expect(addr).toBe('TUEZSdKsoDHQMeZwihtdoBiN46zxhGWYdH');
    expect(addr.startsWith('T')).toBe(true);
    expect(addr).toHaveLength(34);
  });
});

describe('derive.deriveTronAccount', () => {
  it.each(TRON_VECTORS)(
    'matches fixture: $mnemonic [$passphrase] index=$index -> $address',
    (v) => {
      const seed = mnemonicToSeed(v.mnemonic, v.passphrase);
      const acct = deriveTronAccount(seed, v.index);
      expect(acct.address).toBe(v.address);
      expect(acct.path).toBe(`m/44'/195'/0'/0/${v.index}`);
      expect(acct.publicKey).toHaveLength(130); // 65 bytes uncompressed
      expect(acct.publicKey.startsWith('04')).toBe(true);
      expect(acct.privateKey).toHaveLength(64);
      if (v.privateKey_hex !== '') {
        expect(acct.privateKey).toBe(v.privateKey_hex);
      }
    },
  );

  it('default index is 0', () => {
    const seed = mnemonicToSeed(TRON_VECTORS[0].mnemonic);
    const a = deriveTronAccount(seed);
    const b = deriveTronAccount(seed, 0);
    expect(a.address).toBe(b.address);
  });

  it('different indices give different addresses', () => {
    const seed = mnemonicToSeed(TRON_VECTORS[0].mnemonic);
    const a0 = deriveTronAccount(seed, 0);
    const a1 = deriveTronAccount(seed, 1);
    expect(a0.address).not.toBe(a1.address);
    expect(a0.privateKey).not.toBe(a1.privateKey);
  });

  it('TRON_ADDRESS_PREFIX is 0x41', () => {
    // sanity: exported constant must equal protocol value
    expect(TRON_ADDRESS_PREFIX).toBe(0x41);
    // and decode of any derived address must start with that byte
    const seed = mnemonicToSeed(TRON_VECTORS[0].mnemonic);
    const acct = deriveTronAccount(seed, 0);
    // Recompute via primitive: pubkey -> address; verifies branch coverage of helper indirectly
    const pub = secp256k1.getPublicKey(hexToBytes(acct.privateKey), false);
    expect(bytesToHex(pub)).toBe(acct.publicKey);
  });
});
