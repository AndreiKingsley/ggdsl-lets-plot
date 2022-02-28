package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*

val AREA = Geom("area")

class AreaContext : LayerContext() {
    val color = COLOR
    val alpha = ALPHA

    val borderWidth = BORDER_WIDTH
    val borderColor = BORDER_COLOR
}

fun PlotContext.area(block: AreaContext.() -> Unit) {
    layers.add(AreaContext().apply { copyFrom(this@area) }.apply(block).toLayer(AREA))
}