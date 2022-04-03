package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.FATTEN
import com.andreikingsley.ggdsl.letsplot.MAPPABLE_BORDER_COLOR
import com.andreikingsley.ggdsl.letsplot.Y_MAX
import com.andreikingsley.ggdsl.letsplot.Y_MIN

val POINT_RANGE = Geom("pointrange")

class PointRangeContext(): LayerContext(){
    val yMin = Y_MIN
    val yMax = Y_MAX

    // TODO pointSize
    val symbol = SYMBOL
    val size = SIZE
    val pointColor = COLOR

    val alpha = ALPHA

    val fatten = FATTEN

    // todo
    val lineColor = MAPPABLE_BORDER_COLOR
    val width = WIDTH
    val lineType = LINE_TYPE
}

// todo camelcase???
fun PlotContext.pointRange(block: PointRangeContext.() -> Unit) {
    layers.add(PointRangeContext().apply { copyFrom(this@pointRange) }.apply(block).toLayer(POINT_RANGE))
}
