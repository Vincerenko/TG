import { describe, expect, it } from 'vitest';
import { Value } from '@sinclair/typebox/value';
import {
  BroadcastRequest,
  ErrorEnvelope,
  PrepareRequest,
  PrepareResponse,
  QuoteRequest,
  QuoteResponse,
  StatusResponse,
  TronAddress,
  DecimalUsdt,
  TxHash,
  Uuid,
} from './index.js';

describe('primitives', () => {
  it('TronAddress accepts valid base58 addresses', () => {
    expect(Value.Check(TronAddress, 'TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t')).toBe(true);
  });
  it('TronAddress rejects bad prefix or length', () => {
    expect(Value.Check(TronAddress, 'XR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t')).toBe(false);
    expect(Value.Check(TronAddress, 'TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6')).toBe(false);
    expect(Value.Check(TronAddress, 'TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj60I')).toBe(false);
  });

  it('DecimalUsdt accepts integer and fractional amounts', () => {
    expect(Value.Check(DecimalUsdt, '0')).toBe(true);
    expect(Value.Check(DecimalUsdt, '1.5')).toBe(true);
    expect(Value.Check(DecimalUsdt, '1000000.123456')).toBe(true);
  });
  it('DecimalUsdt rejects negative, leading-zeros, scientific, >6 decimals', () => {
    expect(Value.Check(DecimalUsdt, '-1')).toBe(false);
    expect(Value.Check(DecimalUsdt, '01')).toBe(false);
    expect(Value.Check(DecimalUsdt, '1e5')).toBe(false);
    expect(Value.Check(DecimalUsdt, '1.1234567')).toBe(false);
  });

  it('TxHash accepts 64 hex chars', () => {
    expect(Value.Check(TxHash, 'a'.repeat(64))).toBe(true);
    expect(Value.Check(TxHash, 'a'.repeat(63))).toBe(false);
  });

  it('Uuid accepts canonical uuid', () => {
    expect(Value.Check(Uuid, '11111111-2222-3333-4444-555555555555')).toBe(true);
    expect(Value.Check(Uuid, 'not-a-uuid')).toBe(false);
  });
});

describe('transfer schemas roundtrip', () => {
  it('QuoteRequest validates', () => {
    const v = {
      from: 'TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t',
      to: 'TXYZopYRdj2D9XRtbG411XZZ3kM5VkAeBf',
      amount_usdt: '12.5',
    };
    expect(Value.Check(QuoteRequest, v)).toBe(true);
  });

  it('QuoteResponse validates', () => {
    const v = {
      quote_id: '11111111-2222-3333-4444-555555555555',
      fee_usdt: '1.5',
      recipient_active: true,
      energy_needed: 65000,
      expires_at: '2026-01-01T00:00:00.000Z',
    };
    expect(Value.Check(QuoteResponse, v)).toBe(true);
  });

  it('PrepareRequest / PrepareResponse validate', () => {
    expect(Value.Check(PrepareRequest, { quote_id: '11111111-2222-3333-4444-555555555555' })).toBe(
      true,
    );
    const r: import('./index.js').PrepareResponse = {
      unsigned_tx: 'deadbeef',
      energy_delegated_at: '2026-01-01T00:00:00.000Z',
      delegation_tx_hash: 'a'.repeat(64),
    };
    expect(Value.Check(PrepareResponse, r)).toBe(true);
  });

  it('BroadcastRequest validates', () => {
    expect(
      Value.Check(BroadcastRequest, {
        quote_id: '11111111-2222-3333-4444-555555555555',
        signed_tx: 'deadbeef',
      }),
    ).toBe(true);
  });

  it('StatusResponse validates union', () => {
    expect(Value.Check(StatusResponse, { status: 'pending', confirmations: 0 })).toBe(true);
    expect(Value.Check(StatusResponse, { status: 'confirmed', confirmations: 19 })).toBe(true);
    expect(Value.Check(StatusResponse, { status: 'failed', confirmations: 0 })).toBe(true);
    expect(Value.Check(StatusResponse, { status: 'banana', confirmations: 0 })).toBe(false);
  });

  it('ErrorEnvelope validates', () => {
    expect(
      Value.Check(ErrorEnvelope, { error: { code: 'QUOTE_EXPIRED', message: 'expired' } }),
    ).toBe(true);
    expect(
      Value.Check(ErrorEnvelope, { error: { code: 'NOPE', message: 'expired' } }),
    ).toBe(false);
  });
});
