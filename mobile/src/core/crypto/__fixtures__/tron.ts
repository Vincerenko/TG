/**
 * TRON address derivation fixtures (path m/44'/195'/0'/0/{idx}).
 *
 * Computed from the trusted primitive chain:
 *   @scure/bip39 (PBKDF2)  ->  @scure/bip32 (BIP32 ckd)
 *   ->  @noble/curves secp256k1  ->  @noble/hashes keccak_256
 *   ->  @scure/base base58check(sha256)
 *
 * Verified to match TronLink/TronWeb output for the same mnemonics
 * (TronWeb uses byte-identical primitives via tronwebjs/utils/crypto).
 */
export interface TronVector {
  mnemonic: string;
  passphrase: string;
  index: number;
  address: string;
  privateKey_hex: string;
}

export const TRON_VECTORS: readonly TronVector[] = [
  {
    mnemonic:
      'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about',
    passphrase: '',
    index: 0,
    address: 'TUEZSdKsoDHQMeZwihtdoBiN46zxhGWYdH',
    privateKey_hex: 'b5a4cea271ff424d7c31dc12a3e43e401df7a40d7412a15750f3f0b6b5449a28',
  },
  {
    mnemonic:
      'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about',
    passphrase: '',
    index: 1,
    address: 'TSeJkUh4Qv67VNFwY8LaAxERygNdy6NQZK',
    privateKey_hex: 'edb728e259afca2ddcc428459e7681b8414668649aedbc8d25c0872da219b2e6',
  },
  {
    mnemonic:
      'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about',
    passphrase: '',
    index: 5,
    address: 'TBdYXtwq18cAhi1BA574TrP6tw2G86anu1',
    privateKey_hex: 'b46d74cf377ce98d89d7c9eade68eb863791296122e2b1324057fdc190369cd0',
  },
  {
    mnemonic: 'legal winner thank year wave sausage worth useful legal winner thank yellow',
    passphrase: '',
    index: 0,
    address: 'TUJ2YbSDGtCqzRz7quPQidRCMC98jDAPXc',
    privateKey_hex: 'abde94314a030ae8860a58db8adde7fc962cca2455db9d2979e480038c49dcbc',
  },
  {
    mnemonic: 'letter advice cage absurd amount doctor acoustic avoid letter advice cage above',
    passphrase: '',
    index: 0,
    address: 'TBke9PQ4W98MV6SN6Xs8mMedDTwfXge3jh',
    privateKey_hex: 'b811253dfa038e677768fa8dcf710a979480dfd53dc1be3f8aadcbdcb880012d',
  },
  {
    mnemonic: 'zoo zoo zoo zoo zoo zoo zoo zoo zoo zoo zoo wrong',
    passphrase: '',
    index: 0,
    address: 'TCp2vg4MrkHQqTQLtCfGktdgaH99MFR88g',
    privateKey_hex: '501b6e7bc9a1ff6de93df19852bdbf43e55b4217e1cc496dbfc006c7d19a50c4',
  },
  {
    mnemonic:
      'abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about',
    passphrase: 'TREZOR',
    index: 0,
    address: 'TAyDUYP5rcf56xFwrg8cU1qQwvnWpkeapM',
    privateKey_hex: '', // not asserted; cross-checks address-from-passphrase only
  },
];

/** Sign-determinism vector: known raw_data_hex + privateKey -> known sig. */
export const SIGN_VECTOR = {
  raw_data_hex: '0a02b1822208b8e10b59d3b8e2af40d8b0bba2c3304801',
  privateKey_hex: 'b5a4cea271ff424d7c31dc12a3e43e401df7a40d7412a15750f3f0b6b5449a28',
  txid_hex: '616ec2819a1183798a56bc60ed584d91943bfd43b5e0956148c2ba2b7fe35ac8',
  signature_hex:
    'c28cd1c200e2ed6805eb5c68c53e05040d0326475660441c6443af084488fef72d4e118206f24901e54353e0aa3d385d642b60c2e0420b3ca370cac7c220a92f01',
} as const;
