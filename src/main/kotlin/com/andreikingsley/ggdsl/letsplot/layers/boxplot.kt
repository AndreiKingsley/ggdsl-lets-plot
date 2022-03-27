package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.LineContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.*

val BOXPLOT = Geom("boxplot")

class BoxplotContext : LayerContext() {

    val lower = LOWER
    val upper = UPPER
    val middle = MIDDLE
    val yMin = Y_MIN
    val yMax = Y_MAX

    val color = COLOR
    val alpha = ALPHA

    val borderWidth = BORDER_WIDTH
    val borderColor = BORDER_COLOR
}

fun PlotContext.boxplot(block: BoxplotContext.() -> Unit) {
    layers.add(BoxplotContext().apply { copyFrom(this@boxplot) }.apply(block).toLayer(BOXPLOT))
}
