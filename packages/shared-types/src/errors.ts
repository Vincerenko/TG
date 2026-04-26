import { Type, type Static } from '@sinclair/typebox';

export const ErrorCode = Type.Union([
  Type.Literal('VALIDATION_ERROR'),
  Type.Literal('QUOTE_NOT_FOUND'),
  Type.Literal('QUOTE_EXPIRED'),
  Type.Literal('QUOTE_CONSUMED'),
  Type.Literal('SANCTIONED_ADDRESS'),
  Type.Literal('INSUFFICIENT_BALANCE'),
  Type.Literal('TREASURY_UNAVAILABLE'),
  Type.Literal('RATE_LIMITED'),
  Type.Literal('REVIEW_QUEUE'),
  Type.Literal('BROADCAST_FAILED'),
  Type.Literal('INTERNAL'),
]);
export type ErrorCode = Static<typeof ErrorCode>;

export const ErrorEnvelope = Type.Object({
  error: Type.Object({
    code: ErrorCode,
    message: Type.String(),
  }),
});
export type ErrorEnvelope = Static<typeof ErrorEnvelope>;
