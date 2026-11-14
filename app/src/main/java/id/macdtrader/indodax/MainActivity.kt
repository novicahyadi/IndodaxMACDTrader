package id.macdtrader.indodax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.macdtrader.indodax.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme { TradingApp() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TradingApp() {
    var running by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Dashboard") }
    var mode by remember { mutableStateOf("PAPER") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MACD Trader INDODAX") })
        },
        bottomBar = {
            NavigationBar {
                listOf("Dashboard", "Positions", "Settings").forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(when (tab) { "Dashboard" -> "⌂"; "Positions" -> "▤"; else -> "⚙" }) },
                        label = { Text(tab) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedTab == "Dashboard") {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("STATUS BOT", style = MaterialTheme.typography.labelLarge)
                        Text(if (running) "RUNNING" else "STOPPED", style = MaterialTheme.typography.headlineSmall)
                        Text("Mode: $mode  •  Pair: BTC/IDR  •  Timeframe: 5 menit")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { running = true }, enabled = !running, modifier = Modifier.weight(1f)) { Text("START") }
                            OutlinedButton(onClick = { running = false }, enabled = running, modifier = Modifier.weight(1f)) { Text("STOP") }
                        }
                    }
                }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("STRATEGI MACD OREN", style = MaterialTheme.typography.titleMedium)
                        Text("BUY: MACD oren berada di zona luar bawah lalu berbelok naik.")
                        Text("SELL: MACD oren berada di zona luar atas lalu berbelok turun.")
                        Text("Tanpa take profit dan tanpa cut loss.")
                    }
                }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("DATA PASAR", style = MaterialTheme.typography.titleMedium)
                        Text("Harga BTC/IDR: —")
                        Text("MACD oren: —")
                        Text("Sinyal: HOLD")
                        Text("Koneksi INDODAX: BELUM DIHUBUNGKAN")
                    }
                }
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("MODE TRADING", style = MaterialTheme.typography.titleMedium)
                        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                            listOf("PAPER", "LIVE").forEachIndexed { index, value ->
                                SegmentedButton(
                                    selected = mode == value,
                                    onClick = { if (value == "PAPER") mode = value },
                                    shape = SegmentedButtonDefaults.itemShape(index, 2),
                                    enabled = value == "PAPER"
                                ) { Text(if (value == "LIVE") "LIVE (terkunci)" else value) }
                            }
                        }
                        Text("LIVE belum diaktifkan. Uji PAPER dan keamanan wajib selesai terlebih dahulu.",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            } else if (selectedTab == "Positions") {
                Text("POSITIONS", style = MaterialTheme.typography.headlineSmall)
                Text("Riwayat posisi PAPER akan ditampilkan di tahap berikutnya.")
            } else {
                Text("SETTINGS", style = MaterialTheme.typography.headlineSmall)
                Text("Pair: BTC/IDR")
                Text("Timeframe: 5 menit")
                Text("Strategi: MACD oren zona luar")
                Text("API Key belum diminta atau disimpan pada versi fondasi ini.")
            }
            Text("Versi fondasi 0.1.0 — belum mengirim order sungguhan.",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
