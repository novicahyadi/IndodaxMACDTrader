package id.macdtrader.indodax.strategy

import kotlin.math.abs

data class Candle(
    val timestamp: Long,
    val close: Double,
    val isClosed: Boolean = true
)

data class MacdPoint(
    val timestamp: Long,
    val macd: Double,
    val signal: Double,
    val histogram: Double
)

enum class TradingSignal { BUY, SELL, HOLD }

data class StrategyConfig(
    val fastPeriod: Int = 12,
    val slowPeriod: Int = 26,
    val signalPeriod: Int = 9,
    val zoneLookback: Int = 100,
    val outerPercent: Double = 0.10
)

class MacdOuterZoneStrategy(private val config: StrategyConfig = StrategyConfig()) {
    fun calculate(closedCandles: List<Candle>): List<MacdPoint> {
        val closes = closedCandles.filter { it.isClosed }
        require(config.fastPeriod > 0 && config.slowPeriod > config.fastPeriod)
        require(config.signalPeriod > 0)
        if (closes.size < config.slowPeriod + config.signalPeriod + 2) return emptyList()

        val fast = ema(closes.map { it.close }, config.fastPeriod)
        val slow = ema(closes.map { it.close }, config.slowPeriod)
        val macd = fast.indices.map { fast[it] - slow[it] }
        val signal = ema(macd, config.signalPeriod)
        return closes.indices.map { i ->
            MacdPoint(closes[i].timestamp, macd[i], signal[i], macd[i] - signal[i])
        }
    }

    fun detect(points: List<MacdPoint>): TradingSignal {
        if (points.size < config.zoneLookback + 2) return TradingSignal.HOLD
        val current = points.last()
        val previous = points[points.lastIndex - 1]
        val sample = points.drop(points.size - config.zoneLookback - 1).dropLast(1).map { it.macd }.sorted()
        if (sample.isEmpty()) return TradingSignal.HOLD

        // Zona luar dihitung secara internal dari persentil MACD historis tertutup.
        // Tidak ditampilkan sebagai "rentang terbaru" pada UI.
        val lower = percentile(sample, config.outerPercent)
        val upper = percentile(sample, 1.0 - config.outerPercent)
        val turnedUp = current.macd > previous.macd
        val turnedDown = current.macd < previous.macd
        val wasFalling = previous.macd <= points[points.lastIndex - 2].macd
        val wasRising = previous.macd >= points[points.lastIndex - 2].macd

        return when {
            previous.macd <= lower && current.macd <= lower && turnedUp && wasFalling -> TradingSignal.BUY
            previous.macd >= upper && current.macd >= upper && turnedDown && wasRising -> TradingSignal.SELL
            else -> TradingSignal.HOLD
        }
    }

    private fun ema(values: List<Double>, period: Int): List<Double> {
        if (values.isEmpty()) return emptyList()
        val alpha = 2.0 / (period + 1.0)
        val result = MutableList(values.size) { values.first() }
        for (i in 1 until values.size) {
            result[i] = values[i] * alpha + result[i - 1] * (1.0 - alpha)
        }
        return result
    }

    private fun percentile(sorted: List<Double>, p: Double): Double {
        val index = (p.coerceIn(0.0, 1.0) * (sorted.size - 1)).toInt()
        return sorted[index]
    }
}
