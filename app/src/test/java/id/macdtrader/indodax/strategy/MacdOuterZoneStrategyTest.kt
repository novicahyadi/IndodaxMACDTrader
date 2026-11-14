package id.macdtrader.indodax.strategy

import org.junit.Assert.assertEquals
import org.junit.Test

class MacdOuterZoneStrategyTest {
    @Test fun insufficientDataProducesHold() {
        val strategy = MacdOuterZoneStrategy()
        assertEquals(TradingSignal.HOLD, strategy.detect(emptyList()))
    }

    @Test fun macdCalculationReturnsNoValuesForInsufficientCandles() {
        val strategy = MacdOuterZoneStrategy()
        val candles = (0 until 20).map { Candle(it.toLong(), 1_000.0 + it) }
        assertEquals(0, strategy.calculate(candles).size)
    }
}
