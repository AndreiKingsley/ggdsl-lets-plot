package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.LineContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*

val BOXPLOT = Geom("boxplot")

class BoxplotContext : LayerContext() {

    val lower = MappableNonPositionalAes<String>("lower")
    val upper = MappableNonPositionalAes<String>("upper")
    val middle = MappableNonPositionalAes<String>("middle")
    val yMin = MappableNonPositionalAes<String>("ymin")
    val yMax = MappableNonPositionalAes<String>("ymax")

    val color = COLOR
    val alpha = ALPHA

    val borderWidth = BORDER_WIDTH
    val borderColor = BORDER_COLOR
}

fun PlotContext.boxplot(block: BoxplotContext.() -> Unit) {
    layers.add(BoxplotContext().apply { copyFrom(this@boxplot) }.apply(block).toLayer(BOXPLOT))
}
