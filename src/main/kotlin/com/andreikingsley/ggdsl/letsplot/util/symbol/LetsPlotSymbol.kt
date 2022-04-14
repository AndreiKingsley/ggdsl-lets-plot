package com.andreikingsley.ggdsl.letsplot.util.symbol

import com.andreikingsley.ggdsl.util.symbol.Symbol

// todo complete
class LetsPlotSymbol internal constructor(val shape: Int): Symbol{
    companion object {
        val DIAMOND = LetsPlotSymbol(23)
    }
}
