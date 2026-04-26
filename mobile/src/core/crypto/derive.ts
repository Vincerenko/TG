/**
 * BIP44/SLIP-44 TRON account derivation.
 *
 * Path:    m/44'/195'/0'/0/{index}
 * Curve:   secp256k1
 * Address: base58check( 0x41 || keccak256(uncompressed_pubkey[1:])[12:] )
 *
 * Output is byte-identical to TronWeb / TronLink for the same seed.
 */
import { HDKey } from '@scure/bip32';
import { secp256k1 } from '@noble/curves/secp256k1';
import { keccak_256 } from '@noble/hashes/sha3';
import { sha256 } from '@noble/hashes/sha256';
import { base58check } from '@scure/base';
import { bytesToHex } from '@noble/hashes/utils';

export const TRON_COIN_TYPE = 195;
export const TRON_ADDRESS_PREFIX = 0x41;

export interface TronAccount {
  /** base58check, always starts with 'T', 34 chars */
  address: string;
  /** 64 hex chars (32 bytes), no 0x prefix */
  privateKey: string;
  /** 130 hex chars (65 bytes uncompressed, 0x04 prefix) */
  publicKey: string;
  /** BIP44 path used */
  path: string;
}

const b58c = base58check(sha256);

export function tronPath(index = 0): string {
  if (!Number.isInteger(index) || index < 0 || index > 0x7fffffff) {
    throw new RangeError(`Invalid TRON account index: ${index}`);
  }
  return `m/44'/${TRON_COIN_TYPE}'/0'/0/${index}`;
}

export function publicKeyToTronAddress(uncompressedPubKey: Uint8Array): string {
  if (uncompressedPubKey.length !== 65 || uncompressedPubKey[0] !== 0x04) {
    throw new Error('Expected 65-byte uncompressed secp256k1 public key (0x04 prefix)');
  }
  const hash = keccak_256(uncompressedPubKey.subarray(1));
  const addressBytes = new Uint8Array(21);
  addressBytes[0] = TRON_ADDRESS_PREFIX;
  addressBytes.set(hash.subarray(12), 1);
  return b58c.encode(addressBytes);
}

export function deriveTronAccount(seed: Uint8Array, index = 0): TronAccount {
  const path = tronPath(index);
  const node = HDKey.fromMasterSeed(seed).derive(path);
  const privateKey = node.privateKey!;
  const publicKey = secp256k1.getPublicKey(privateKey, false);
  return {
    address: publicKeyToTronAddress(publicKey),
    privateKey: bytesToHex(privateKey),
    publicKey: bytesToHex(publicKey),
    path,
  };
}
