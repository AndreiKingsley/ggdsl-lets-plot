package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.LayerContext
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.dsl.toLayer
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.FATTEN
import com.andreikingsley.ggdsl.letsplot.MIDDLE
import com.andreikingsley.ggdsl.letsplot.Y_MAX
import com.andreikingsley.ggdsl.letsplot.Y_MIN
import jetbrains.letsPlot.geom.geomCrossbar
import jetbrains.letsPlot.geom.geomErrorBar

val CROSSBAR = Geom("crossbar")

class CrossBarContext(): LayerContext(){
    val yMin = Y_MIN
    val yMax = Y_MAX
    val middle = MIDDLE

    val fatten = FATTEN

    //val size = SIZE
    val color = COLOR
    val alpha = ALPHA

    val borderColor = BORDER_COLOR

    // TODO LINE WIDTH??? BORDER WIDTH??
    val borderWidth = BORDER_WIDTH
    val lineType = LINE_TYPE
}

fun PlotContext.crossbar(block: CrossBarContext.() -> Unit) {
    layers.add(CrossBarContext().apply { copyFrom(this@crossbar) }.apply(block).toLayer(CROSSBAR))
}