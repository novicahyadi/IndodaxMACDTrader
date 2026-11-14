# MACD Trader INDODAX — Fondasi Android

Target: Android 13 / Xiaomi Redmi Note 10. Proyek dibangun dari HP melalui GitHub Actions.

## Status versi 0.1.0
- Fondasi Kotlin + Jetpack Compose.
- Dashboard awal, tab Positions dan Settings.
- Tombol START/STOP tampilan.
- PAPER dipilih sebagai mode awal; LIVE masih terkunci.
- Kerangka perhitungan MACD 12/26/9 dan deteksi zona luar.
- Workflow GitHub Actions untuk membuat debug APK.

## Definisi zona luar pada fondasi ini
Agar strategi visual bisa diuji secara matematis, kode awal menggunakan persentil 10% dan 90% dari 100 candle MACD tertutup sebelumnya. BUY memerlukan MACD berada di ekor bawah dan berbalik naik; SELL memerlukan MACD berada di ekor atas dan berbalik turun. Ini adalah asumsi implementasi awal yang harus diuji dan dapat disetel sebelum dipakai trading. Tidak ada order LIVE pada versi ini.

## Build dari HP Android
1. Buat repository GitHub baru bernama `IndodaxMacdTrader`.
2. Upload seluruh isi folder proyek ini ke repository (jangan upload folder ZIP sebagai satu file).
3. Buka tab Actions pada repository.
4. Jalankan workflow `Android APK Build` atau push ke branch `main`.
5. Setelah selesai, buka hasil workflow dan unduh artifact `MACD-Trader-INDODAX-debug`.
6. Ekstrak ZIP artifact di HP lalu instal APK. Izinkan instalasi dari sumber tersebut jika Android meminta.

## Belum dikerjakan
- Pengambilan candle BTC/IDR dari API publik.
- Grafik harga dan panel MACD live.
- Simulasi saldo, fee, order, partial fill, dan P/L.
- Room database dan DataStore.
- Foreground Service dan pemulihan proses.
- Integrasi Trade API V2, signing, dan rekonsiliasi order.
- Enkripsi credential Android Keystore.
- Pengujian strategi dengan data historis.
- LIVE trading (tetap terkunci).

Jangan masukkan API Key/Secret ke source code, GitHub, screenshot, atau chat. Jangan gunakan API permission withdrawal.
