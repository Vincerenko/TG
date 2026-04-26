import { Type, type Static } from '@sinclair/typebox';

/** TRON mainnet/testnet base58 address: starts with T, 34 chars, base58 alphabet. */
export const TronAddress = Type.String({
  pattern: '^T[1-9A-HJ-NP-Za-km-z]{33}$',
  minLength: 34,
  maxLength: 34,
  description: 'TRON base58check address',
});
export type TronAddress = Static<typeof TronAddress>;

/** Decimal string with up to 6 fractional digits, no scientific notation, non-negative. */
export const DecimalUsdt = Type.String({
  pattern: '^(0|[1-9][0-9]{0,11})(\\.[0-9]{1,6})?$',
  description: 'USDT amount as decimal string, up to 6 decimals',
});
export type DecimalUsdt = Static<typeof DecimalUsdt>;

export const TxHash = Type.String({
  pattern: '^[0-9a-fA-F]{64}$',
  minLength: 64,
  maxLength: 64,
});
export type TxHash = Static<typeof TxHash>;

export const HexString = Type.String({
  pattern: '^[0-9a-fA-F]+$',
});
export type HexString = Static<typeof HexString>;

export const Uuid = Type.String({
  pattern: '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$',
});
export type Uuid = Static<typeof Uuid>;

/** ISO-8601 timestamp with millisecond precision and Z suffix. */
export const IsoTimestamp = Type.String({
  pattern: '^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?Z$',
});
export type IsoTimestamp = Static<typeof IsoTimestamp>;
