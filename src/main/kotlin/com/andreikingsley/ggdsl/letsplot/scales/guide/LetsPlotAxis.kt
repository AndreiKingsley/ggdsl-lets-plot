package com.andreikingsley.ggdsl.letsplot.scales.guide

import com.andreikingsley.ggdsl.dsl.PositionalScaleContext
import com.andreikingsley.ggdsl.ir.scale.guide.Axis

class LetsPlotAxis<DomainType: Any>: Axis {
    var name: String? = null
    var breaks = listOf<DomainType>()
    var labels = listOf<String>() // todo pair list and format

    // breaks(.... format = ) / labeledBreaks(0.0 to "0", 0.4 to ".4" ...)
    // todo expand & trans
}

fun<DomainType : Any> PositionalScaleContext<DomainType>.axis(block: LetsPlotAxis<DomainType>.() -> Unit) {
    axis = LetsPlotAxis<DomainType>().apply(block)
}