package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.*

val BOXPLOT = Geom("boxplot")

class BoxplotContext(override var data: MutableNamedData) : LayerContext() {

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
    layers.add(BoxplotContext(data).apply { copyFrom(this@boxplot) }.apply(block).toLayer(BOXPLOT))
}
