/**
 * BIP39 mnemonic + seed helpers.
 *
 * Entropy comes from @noble/hashes randomBytes -> crypto.getRandomValues.
 * In React Native this requires `react-native-get-random-values` to be imported
 * once at app entry (App.tsx) before any crypto module loads.
 */
import {
  generateMnemonic as bip39Generate,
  mnemonicToEntropy,
  mnemonicToSeedSync,
  validateMnemonic as bip39Validate,
} from '@scure/bip39';
import { wordlist } from '@scure/bip39/wordlists/english';

export type Strength = 128 | 160 | 192 | 224 | 256;

export interface GeneratedSeed {
  mnemonic: string;
  entropy: Uint8Array;
}

export function generateSeed(strength: Strength = 128): GeneratedSeed {
  const mnemonic = bip39Generate(wordlist, strength);
  const entropy = mnemonicToEntropy(mnemonic, wordlist);
  return { mnemonic, entropy };
}

export function validateMnemonic(mnemonic: string): boolean {
  return bip39Validate(mnemonic, wordlist);
}

export class InvalidMnemonicError extends Error {
  constructor() {
    super('Invalid BIP39 mnemonic (checksum or wordlist mismatch)');
    this.name = 'InvalidMnemonicError';
  }
}

export function mnemonicToSeed(mnemonic: string, passphrase = ''): Uint8Array {
  if (!validateMnemonic(mnemonic)) {
    throw new InvalidMnemonicError();
  }
  return mnemonicToSeedSync(mnemonic, passphrase);
}
