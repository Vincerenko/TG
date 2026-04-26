import { describe, expect, it } from 'vitest';
import * as crypto from './index.js';

describe('crypto barrel exports', () => {
  it('re-exports the public API', () => {
    expect(typeof crypto.generateSeed).toBe('function');
    expect(typeof crypto.validateMnemonic).toBe('function');
    expect(typeof crypto.mnemonicToSeed).toBe('function');
    expect(typeof crypto.deriveTronAccount).toBe('function');
    expect(typeof crypto.publicKeyToTronAddress).toBe('function');
    expect(typeof crypto.tronPath).toBe('function');
    expect(typeof crypto.signTransaction).toBe('function');
    expect(typeof crypto.txIdFromRawData).toBe('function');
    expect(crypto.InvalidMnemonicError).toBeDefined();
    expect(crypto.TRON_COIN_TYPE).toBe(195);
    expect(crypto.TRON_ADDRESS_PREFIX).toBe(0x41);
  });
});
