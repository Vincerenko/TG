import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    include: ['src/**/*.test.ts'],
    coverage: {
      provider: 'v8',
      reporter: ['text', 'json-summary', 'html'],
      include: ['src/core/crypto/**/*.ts'],
      exclude: ['src/**/*.test.ts'],
      thresholds: {
        // R-rule: crypto core MUST be 100% line coverage
        lines: 100,
        statements: 100,
        functions: 100,
        branches: 100,
      },
    },
  },
});
