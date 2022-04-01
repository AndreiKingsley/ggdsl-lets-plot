package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.scale.*
import com.andreikingsley.ggdsl.util.color.*
import com.andreikingsley.ggdsl.util.symbol.*
import com.andreikingsley.ggdsl.letsplot.facet.*
import com.andreikingsley.ggdsl.letsplot.layers.*
import com.andreikingsley.ggdsl.letsplot.position.*
import com.andreikingsley.ggdsl.util.linetype.CommonLineType
import jetbrains.letsPlot.*
import jetbrains.letsPlot.facet.facetGrid
import jetbrains.letsPlot.intern.Options
import jetbrains.letsPlot.intern.OptionsMap
import jetbrains.letsPlot.intern.layer.PosOptions
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.scale.*

class LayerWrapper(private val layer: Layer) :
    jetbrains.letsPlot.intern.layer.LayerBase(
        data = null,
        mapping = Options(layer.mappings.map { (aes, value) ->
            wrapBinding(aes, value, layer.geom) }.toMap()
        ),
        geom = layer.geom.toLPGeom(!(layer.settings.containsKey(SYMBOL) || layer.mappings.containsKey(SYMBOL))),
        stat = Stat.identity,
        position = layer.features[POSITION_FEATURE_NAME]?.toPos() ?: Pos.dodge, // TODO
        showLegend = true,
    ) {
    override fun seal() = Options(layer.settings.map { (aes, value) -> wrapBinding(aes, value, layer.geom) }.toMap())
}

private fun LayerFeature?.toPos(): PosOptions? {
    return when(this) {
        is Position.Identity -> return Pos.identity
        is Position.Stack -> return Pos.stack
        is Position.Dodge -> return positionDodge(width)
        is Position.Jitter -> return positionJitter(width, height)
        is Position.Nudge -> return positionNudge(x, y)
        is Position.JitterDodge -> positionJitterDodge(dodgeWidth, jitterWidth, jitterHeight)
        else -> null
    }
}

// TODO
fun wrapBinding(aes: Aes, value: Any, geom: Geom): Pair<String, Any> {
    if (aes == SYMBOL) {
        val ret = "shape" to wrapValue(value) // TODO scaling
       // TODO
        return ret
    }
    return aes.toLPName(geom) to wrapValue(value)
}

// TODO
fun wrapValue(value: Any): Any{
    if (value is StandardColor) {
        return value.description
    }
    if (value is CommonSymbol) {
        return wrapSymbol(value)
    }
    if (value is CommonLineType) {
        return value.description
    }
    return value
}

val symbolToNumber = mapOf(
    Symbol.RECTANGLE to 22,
    Symbol.CIRCLE to 21,
    Symbol.TRIANGLE to 24,
)

fun wrapSymbol(symbol: Symbol): Int = symbolToNumber[symbol]!!

// TODO
fun Aes.toLPName(geom: Geom): String {
    if (this == LINE_TYPE){
        return "linetype"
    }
    if ((geom == Geom.BAR || geom == Geom.POINT || geom == BOXPLOT || geom == AREA) && this == COLOR) {
        return "fill"
    }

    if (this == BORDER_WIDTH) {
        if (geom == BOXPLOT) {
            return "width"
        }
        if (geom == AREA) {
            return "size"
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
        Geom.LINE -> jetbrains.letsPlot.Geom.path()
        BOXPLOT -> jetbrains.letsPlot.Geom.boxplot()
        AREA -> jetbrains.letsPlot.Geom.area()
        ERROR_BAR -> jetbrains.letsPlot.Geom.errorbar()
        else -> TODO()
    }
}


fun Scale.wrap(aes: Aes, geom: Geom): jetbrains.letsPlot.intern.Scale? {
    // TODO depends on geom
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
                    scaleFillManual(values = values.map { (it as StandardColor).description })
                }
                // TODO
                ALPHA -> scaleAlphaManual(values = values.map { it as Double }) // TODO
                else -> TODO()
            }
        }
        is ContinuousNonPositionalScale<*, *> -> {
            when (aes) {
                SIZE -> scaleSize(limits = domainLimits.toLP(), range = range.toLP()) // TODO
                COLOR -> {
                    val (lowColor, highColor) = range.let {
                        it?.first as? StandardColor to it?.second as? StandardColor
                    }
                    scaleColorContinuous(
                        low = lowColor?.description,
                        high = highColor?.description,
                        limits = domainLimits.toLP() // todo datetime here
                    )
                }   // TODO
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
        /* TODO
        xFormat = xFormat,
        yFormat = yFormat
         */
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
    return plot + with(layout.size) {
        ggsize(first, second)
    }
}
