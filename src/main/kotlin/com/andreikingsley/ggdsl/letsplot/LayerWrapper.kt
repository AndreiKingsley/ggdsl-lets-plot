package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.*
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.scale.*
import com.andreikingsley.ggdsl.ir.symbol.CIRCLE
import com.andreikingsley.ggdsl.ir.symbol.RECTANGLE
import com.andreikingsley.ggdsl.ir.symbol.Symbol
import com.andreikingsley.ggdsl.ir.symbol.TRIANGLE
import com.andreikingsley.ggdsl.letsplot.facet.FACET_GRID_FEATURE
import com.andreikingsley.ggdsl.letsplot.facet.FACET_X
import com.andreikingsley.ggdsl.letsplot.facet.FACET_Y
import com.andreikingsley.ggdsl.letsplot.facet.FacetGridFeature
import com.andreikingsley.ggdsl.letsplot.layers.BOXPLOT
import jetbrains.letsPlot.Pos
import jetbrains.letsPlot.Stat
import jetbrains.letsPlot.facet.facetGrid
import jetbrains.letsPlot.intern.Options
import jetbrains.letsPlot.intern.OptionsMap
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.*

class LayerWrapper(private val layer: Layer) :
    jetbrains.letsPlot.intern.layer.LayerBase(
        data = layer.data,
        mapping = Options(layer.mappings.map { (aes, value) -> wrapBinding(aes, value, layer.geom) }.toMap()),
        geom = layer.geom.toLPGeom(!layer.settings.containsKey(SYMBOL)),
        stat = Stat.identity,
        position = Pos.dodge, // TODO
        showLegend = true,
    ) {
    override fun seal() = Options(layer.settings.map { (aes, value) -> wrapBinding(aes, value, layer.geom) }.toMap())
}

// TODO
fun wrapBinding(aes: Aes, value: Any, geom: Geom): Pair<String, Any> {
    if (aes == SYMBOL) {
        return "shape" to wrapSymbol(value as Symbol)
    }
    return aes.toLPName(geom) to value
}

val symbolToNumber = mapOf(
    RECTANGLE to 22,
    CIRCLE to 21,
    TRIANGLE to 24,
)

fun wrapSymbol(symbol: Symbol): Int = symbolToNumber[symbol]!!

// TODO
fun Aes.toLPName(geom: Geom): String {
    if ((geom == Geom.BAR || geom == Geom.POINT || geom == BOXPLOT) && this == COLOR) {
        return "fill"
    }
    if (this == BORDER_WIDTH) {
        if (geom == BOXPLOT) {
            return "width"
        }
        return "stroke"
    }
    if (this == BORDER_COLOR) {
        return "color"
    }
    if (geom == Geom.LINE && this == WIDTH) {
        return "size"
    }
    return name
}

// TODO rewrite
fun Geom?.toLPGeom(defaultShape: Boolean = true): jetbrains.letsPlot.intern.layer.GeomOptions {
    return when (this!!) {
        Geom.POINT -> {
            if (defaultShape) {
                jetbrains.letsPlot.Geom.point(shape = 21)
            } else {
                jetbrains.letsPlot.Geom.point()
            }
        }
        Geom.BAR -> jetbrains.letsPlot.Geom.bar()
        Geom.LINE -> jetbrains.letsPlot.Geom.line()
        BOXPLOT -> jetbrains.letsPlot.Geom.boxplot()
        else -> TODO()
    }
}


fun Scale.wrap(aes: Aes, geom: Geom): jetbrains.letsPlot.intern.Scale? {

    return when (this) {
        is CategoricalPositionalScale<*> -> {
            when (aes) {
                X -> scaleXDiscrete(limits = categories, name = axis.name)
                Y -> scaleYDiscrete(limits = categories, name = axis.name)
                else -> TODO()
            }
        }
        is ContinuousPositionalScale<*> -> {
            when (aes) {
                X -> scaleXContinuous(limits = limits.toLP(), name = axis.name)
                Y -> scaleYContinuous(limits = limits.toLP(), name = axis.name)
                else -> TODO()
            }
        }
        is CategoricalNonPositionalScale<*, *> -> {
            when (aes) {
                SIZE -> scaleSizeManual(values = values.map { it as Double }) // TODO
                // TODO
                COLOR -> if (values.isEmpty()) {
                    scaleFillDiscrete()
                } else {
                    scaleFillManual(values = values)
                }
                // TODO
                ALPHA -> scaleAlphaManual(values = values.map { it as Double }) // TODO
                else -> TODO()
            }
        }
        is ContinuousNonPositionalScale<*, *> -> {
            when (aes) {
                SIZE -> scaleSize(limits = domainLimits.toLP(), range = range.toLP()) // TODO
                COLOR -> scaleColorContinuous(
                    low = range?.first.toString(),
                    high = range?.second.toString(),
                    limits = domainLimits.toLP()
                )   // TODO
                ALPHA -> scaleAlpha(limits = domainLimits.toLP(), range = range.toLP()) // TODO
                else -> TODO()
            }
        }
        is DefaultScale -> {
            return null
        }
        else -> TODO()
    }

}

// TODO
fun Pair<Any, Any>?.toLP() = this?.let { (it.first as Number) to (it.second as Number) }

fun FacetGridFeature.wrap(): OptionsMap {
    return facetGrid(
        x = mappings[FACET_X]?.id,
        y = mappings[FACET_Y]?.id,
        xOrder = xOrder.value,
        yOrder = yOrder.value,
        xFormat = xFormat,
        yFormat = yFormat
    )
}

fun Plot.toPlot(): jetbrains.letsPlot.intern.Plot {
    var plot = layers.fold(letsPlot(dataset) + labs(title = layout.title)) { plot, layer ->
        var buffer = plot + LayerWrapper(layer)
        layer.scales.forEach { (aes, scale) -> scale.wrap(aes, layer.geom)?.let { buffer += it } }
        buffer
    }
    for ((featureName, feature) in features) {
        when (featureName) {
            FACET_GRID_FEATURE -> {
                plot += (feature as FacetGridFeature).wrap()
            }
        }
    }
    return plot
}
