package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.Y_MAX
import com.andreikingsley.ggdsl.letsplot.Y_MIN

val ERROR_BAR = Geom("errorbar")

class ErrorBarContext(): LayerContext(){
    val yMin = Y_MIN
    val yMax = Y_MAX

    val size = SIZE
    val color = COLOR
    val alpha = ALPHA

    val width = WIDTH
    val lineType = LINE_TYPE
}

fun PlotContext.errorBar(block: ErrorBarContext.() -> Unit) {
    layers.add(ErrorBarContext().apply { copyFrom(this@errorBar) }.apply(block).toLayer(ERROR_BAR))
}
