package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.Y_MAX
import com.andreikingsley.ggdsl.letsplot.Y_MIN

val LINE_RANGE = Geom("linerange")

class LineRangeContext(): LayerContext(){
    val yMin = Y_MIN
    val yMax = Y_MAX

    val size = SIZE
    val color = COLOR
    val alpha = ALPHA

    val width = WIDTH
    val lineType = LINE_TYPE
}

// todo camelcase???
fun PlotContext.lineRange(block: LineRangeContext.() -> Unit) {
    layers.add(LineRangeContext().apply { copyFrom(this@lineRange) }.apply(block).toLayer(LINE_RANGE))
}
