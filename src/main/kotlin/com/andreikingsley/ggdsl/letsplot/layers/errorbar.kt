package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.letsplot.Y_MAX
import com.andreikingsley.ggdsl.letsplot.Y_MIN
import com.andreikingsley.ggdsl.util.color.Color
import com.andreikingsley.ggdsl.util.linetype.LineType

val ERRORBAR = Geom("errorbar")

class ErrorBarContext(override var data: MutableNamedData) : LayerContext(){
    val yMin = Y_MIN
    val yMax = Y_MAX

    val size = SIZE
    val color = COLOR
    val alpha = ALPHA

    val width = WIDTH
    val lineType = LINE_TYPE
}

/**
 * Adds a new error bar layer.
 *
 * Creates a context in which you can create bindings using aesthetic attribute properties invocation:
 * ```
 * boxplot {
 *    x(source<Double>("time")) // mapping from data source to size value
 *    borderColor(Color.BLUE) // setting of constant color value
 * }
 * ```
 *
 *  ### Aesthetic attributes:
 *
 *  Positional:
 *
 *  - [ x][ErrorBarContext.x]
 *  - [y][ErrorBarContext.y] // TODO - move to another geom???
 *
 *  Initial mappings to positional attributes are inherited from the parent [PlotContext] (if they exist).
 *
 *  Sub-positional:
 *  - [yMin][ErrorBarContext.yMin]
 *  - [yMax][ErrorBarContext.yMax]
 *
 *   Non-positional:
 *  - [color][ErrorBarContext.color] - this error bar color, of the type [Color], mappable.
 *  - [alpha][ErrorBarContext.alpha] - this error bar alpha, of the type [Double], mappable.
 *  - [size][ErrorBarContext.size] - this error bar size, of the type [Double], mappable.
 *  - [width][ErrorBarContext.width] - this error bar width, of the type [Double], mappable.
 *  - [lineType][ErrorBarContext.lineType] - this error bar border line type, of the type [LineType], non-mappable.
 *
 *  By default, the dataset inherited from the parent [PlotContext] is used,
 *  but can be overridden with an assignment to the [data][ErrorBarContext.data].
 *
 *  @see [BaseBindingContext]
 */
fun PlotContext.errorBar(block: ErrorBarContext.() -> Unit) {
    layers.add(ErrorBarContext(data).apply { copyFrom(this@errorBar) }.apply(block).toLayer(ERRORBAR))
}
