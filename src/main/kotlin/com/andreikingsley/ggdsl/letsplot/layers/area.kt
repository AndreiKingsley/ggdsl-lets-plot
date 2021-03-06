package com.andreikingsley.ggdsl.letsplot.layers

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.util.color.Color
import com.andreikingsley.ggdsl.dsl.BaseBindingContext

val AREA = Geom("area")

class AreaContext(override var data: MutableNamedData) : LayerContext() {
    val color = COLOR
    val alpha = ALPHA

    val borderWidth = BORDER_WIDTH
    val borderColor = BORDER_COLOR
}

/**
 * Adds a new area layer.
 *
 * Creates a context in which you can create bindings using aesthetic attribute properties invocation:
 * ```
 * area {
 *    x(source<Double>("time")) // mapping from data source to size value
 *    borderColor(Color.BLUE) // setting of constant color value
 * }
 * ```
 *
 *  ### Aesthetic attributes:
 *
 *  Positional:
 *
 *  - [ x][AreaContext.x]
 *  - [y][AreaContext.y]
 *
 *  Initial mappings to positional attributes are inherited from the parent [PlotContext] (if they exist).
 *
 *   Non-positional:
 *  - [color][AreaContext.color] - this area fill color, of the type [Color], mappable TODO.
 *  - [alpha][AreaContext.alpha] - this area fill alpha, of the type [Double], mappable TODO.
 *  - [borderColor][AreaContext.borderColor] - this area border color, of the type [Color], non-mappable.
 *  - [borderWidth][AreaContext.borderWidth] - this area border width, of the type [Double], non-mappable.
 *
 *  By default, the dataset inherited from the parent [PlotContext] is used,
 *  but can be overridden with an assignment to the [data][AreaContext.data].
 *
 *  @see [BaseBindingContext]
 */
fun PlotContext.area(block: AreaContext.() -> Unit) {
    layers.add(AreaContext(data).apply { copyFrom(this@area) }.apply(block).toLayer(AREA))
}
