# Development Status

## Completed in 0.1.0
- [x] Android project skeleton (Kotlin / Compose / Material 3).
- [x] Dashboard placeholder and bottom navigation.
- [x] Start/Stop UI controls (not yet connected to a trading service).
- [x] PAPER selected by default; LIVE disabled in UI.
- [x] MACD 12/26/9 calculation foundation.
- [x] Outer-zone reversal detector foundation.
- [x] GitHub Actions debug APK workflow.

## Pending
- [ ] Verify build in GitHub Actions; no successful build is claimed yet.
- [ ] Confirm mathematical outer-zone definition against the user's intended chart.
- [ ] Public market data and 5-minute closed-candle aggregation.
- [ ] Historical chart and MACD visualization.
- [ ] Paper wallet, orders, positions, fees, P/L, persistence.
- [ ] Foreground service and Android 13 notification permission flow.
- [ ] Official INDODAX Trade API V2 read-only integration.
- [ ] API key encryption and permission validation.
- [ ] Live order submission, reconciliation, and emergency stop (only after tests).

## Known limitations
- The current dashboard uses placeholder market values.
- Start/Stop currently changes UI state only; it does not start or stop a background bot.
- No exchange API calls or real orders exist in this version.
- The percentile-based outer-zone threshold is a provisional mathematical interpretation.
