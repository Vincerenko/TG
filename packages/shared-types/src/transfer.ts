import { Type, type Static } from '@sinclair/typebox';
import { DecimalUsdt, HexString, IsoTimestamp, TronAddress, TxHash, Uuid } from './primitives.js';

export const QuoteRequest = Type.Object({
  from: TronAddress,
  to: TronAddress,
  amount_usdt: DecimalUsdt,
});
export type QuoteRequest = Static<typeof QuoteRequest>;

export const QuoteResponse = Type.Object({
  quote_id: Uuid,
  fee_usdt: DecimalUsdt,
  recipient_active: Type.Boolean(),
  energy_needed: Type.Integer({ minimum: 0 }),
  expires_at: IsoTimestamp,
});
export type QuoteResponse = Static<typeof QuoteResponse>;

export const PrepareRequest = Type.Object({
  quote_id: Uuid,
});
export type PrepareRequest = Static<typeof PrepareRequest>;

export const PrepareResponse = Type.Object({
  unsigned_tx: HexString,
  energy_delegated_at: IsoTimestamp,
  delegation_tx_hash: TxHash,
});
export type PrepareResponse = Static<typeof PrepareResponse>;

export const BroadcastRequest = Type.Object({
  quote_id: Uuid,
  signed_tx: HexString,
});
export type BroadcastRequest = Static<typeof BroadcastRequest>;

export const BroadcastResponse = Type.Object({
  tx_hash: TxHash,
});
export type BroadcastResponse = Static<typeof BroadcastResponse>;

export const TransferStatus = Type.Union([
  Type.Literal('pending'),
  Type.Literal('confirmed'),
  Type.Literal('failed'),
]);
export type TransferStatus = Static<typeof TransferStatus>;

export const StatusResponse = Type.Object({
  status: TransferStatus,
  confirmations: Type.Integer({ minimum: 0 }),
});
export type StatusResponse = Static<typeof StatusResponse>;
