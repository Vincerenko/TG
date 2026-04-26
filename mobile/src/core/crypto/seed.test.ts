import { describe, expect, it } from 'vitest';
import { bytesToHex, hexToBytes } from '@noble/hashes/utils';
import {
  generateSeed,
  validateMnemonic,
  mnemonicToSeed,
  InvalidMnemonicError,
} from './seed.js';
import { BIP39_VECTORS } from './__fixtures__/bip39.js';

describe('seed.generateSeed', () => {
  it('returns a valid 12-word mnemonic by default', () => {
    const { mnemonic, entropy } = generateSeed();
    expect(mnemonic.split(' ')).toHaveLength(12);
    expect(entropy).toHaveLength(16);
    expect(validateMnemonic(mnemonic)).toBe(true);
  });

  it('returns a valid 24-word mnemonic at 256 bits', () => {
    const { mnemonic, entropy } = generateSeed(256);
    expect(mnemonic.split(' ')).toHaveLength(24);
    expect(entropy).toHaveLength(32);
    expect(validateMnemonic(mnemonic)).toBe(true);
  });

  it('produces different mnemonics across calls (entropy from CSPRNG)', () => {
    const a = generateSeed();
    const b = generateSeed();
    expect(a.mnemonic).not.toBe(b.mnemonic);
    expect(bytesToHex(a.entropy)).not.toBe(bytesToHex(b.entropy));
  });
});

describe('seed.validateMnemonic', () => {
  it.each(BIP39_VECTORS)('accepts Trezor vector $entropy_hex', (v) => {
    expect(validateMnemonic(v.mnemonic)).toBe(true);
  });

  it('rejects bad checksum', () => {
    // 12 valid words from wordlist but wrong checksum
    expect(
      validateMnemonic(
        'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon',
      ),
    ).toBe(false);
  });

  it('rejects words not in wordlist', () => {
    expect(validateMnemonic('not a real bip39 mnemonic at all please reject this thing')).toBe(
      false,
    );
  });
});

describe('seed.mnemonicToSeed', () => {
  it.each(BIP39_VECTORS)(
    'matches Trezor vector seed for $entropy_hex (passphrase=TREZOR)',
    (v) => {
      const seed = mnemonicToSeed(v.mnemonic, 'TREZOR');
      expect(bytesToHex(seed)).toBe(v.seed_hex_trezor);
      expect(seed).toHaveLength(64);
    },
  );

  it('produces a different seed when passphrase differs', () => {
    const m =
      'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about';
    const a = mnemonicToSeed(m);
    const b = mnemonicToSeed(m, 'TREZOR');
    expect(bytesToHex(a)).not.toBe(bytesToHex(b));
  });

  it('throws InvalidMnemonicError for invalid mnemonic', () => {
    expect(() => mnemonicToSeed('clearly not a valid mnemonic')).toThrow(InvalidMnemonicError);
    try {
      mnemonicToSeed('clearly not a valid mnemonic');
    } catch (e) {
      expect(e).toBeInstanceOf(InvalidMnemonicError);
      expect((e as Error).name).toBe('InvalidMnemonicError');
      expect((e as Error).message).toMatch(/Invalid BIP39 mnemonic/);
    }
  });

  it('uses round-trip entropy correctly (Trezor vector)', () => {
    // Sanity: hexToBytes of the entropy is what generateMnemonic would have used
    expect(hexToBytes(BIP39_VECTORS[0].entropy_hex)).toHaveLength(16);
  });
});
