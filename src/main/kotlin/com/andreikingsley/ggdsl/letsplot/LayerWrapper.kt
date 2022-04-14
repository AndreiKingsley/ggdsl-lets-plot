package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.*
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.bindings.*
import com.andreikingsley.ggdsl.ir.scale.*
import com.andreikingsley.ggdsl.util.color.*
import com.andreikingsley.ggdsl.util.symbol.*
import com.andreikingsley.ggdsl.letsplot.facet.*
import com.andreikingsley.ggdsl.letsplot.layers.*
import com.andreikingsley.ggdsl.letsplot.position.*
import com.andreikingsley.ggdsl.letsplot.scales.guide.*
import com.andreikingsley.ggdsl.letsplot.util.linetype.LetsPlotLineType
import com.andreikingsley.ggdsl.letsplot.util.symbol.LetsPlotSymbol
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
        mapping = Options(layer.mappings.map { (_, mapping) -> mapping.wrap(layer.geom) }.toMap()),
        /*map { (aes, value) ->
            wrapBinding(aes, value, layer.geom) }.toMap()
        )
        ,
         */
        geom = layer.geom.toLPGeom(!(layer.settings.containsKey(SYMBOL) || layer.mappings.containsKey(SYMBOL))),
        stat = Stat.identity,
        position = layer.features[POSITION_FEATURE_NAME]?.toPos() ?: Pos.identity, // TODO
        showLegend = true,
    ) {
    // TODO
    override fun seal() = Options(
        layer.settings.map {
                (_, setting) -> (setting as NonPositionalSetting<*>).wrap(layer.geom)
        }.toMap()
    )
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

fun Mapping<*>.wrap(geom: Geom): Pair<String, String> {
    return aes.toLPName(geom) to source.id
}

fun NonPositionalSetting<*>.wrap(geom: Geom): Pair<String, Any>{
    return aes.toLPName(geom) to wrapValue(value)
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
    if (value is LetsPlotSymbol) {
        return value.shape
    }
    if (value is CommonLineType) {
        return value.description
    }
    if (value is LetsPlotLineType) {
        return value.description
    }
    return value
}

fun commonSymbolToShape(symbol: CommonSymbol): Int {
    return when(symbol){
        Symbol.RECTANGLE -> 22
        Symbol.CIRCLE -> 21
        Symbol.TRIANGLE -> 24
        else -> TODO()
    }
}

fun wrapSymbol(symbol: Symbol): Int {
    return when(symbol){
        is LetsPlotSymbol -> symbol.shape
        is CommonSymbol -> commonSymbolToShape(symbol)
        else -> TODO()
    }
}

val fillGeoms = setOf(
    Geom.BAR,
    Geom.POINT,
    BOXPLOT,
    AREA,
    CROSSBAR,
    // TODO
    POINT_RANGE,
)
// TODO
fun Aes.toLPName(geom: Geom): String {
    if (this == SYMBOL) {
        return "shape"
    }
    if (this == LINE_TYPE){
        return "linetype"
    }
    if (geom in fillGeoms && this == COLOR) {
        return "fill"
    }

    if (this == BORDER_WIDTH) {
        if (geom == BOXPLOT) {
            return "width"
        }
        if (geom == AREA || geom == CROSSBAR) {
            return "size"
        }
        return "stroke"
    }
    if (this == BORDER_COLOR || this == MAPPABLE_BORDER_COLOR) {
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
        ERRORBAR -> jetbrains.letsPlot.Geom.errorbar()
        CROSSBAR -> jetbrains.letsPlot.Geom.crossbar()
        LINE_RANGE-> jetbrains.letsPlot.Geom.linerange()
        POINT_RANGE -> jetbrains.letsPlot.Geom.pointrange()
        else -> TODO()
    }
}


fun Scale.wrap(aes: Aes, geom: Geom): jetbrains.letsPlot.intern.Scale? {
    // TODO depends on geom
    return when (this) {
        is PositionalScale<*> -> {
            val axis = axis as? LetsPlotAxis<*>
            val name = axis?.name
            val breaks = axis?.breaks
            val labels = axis?.labels
            when(this) {
                is CategoricalPositionalScale<*> -> {
                    when (aes) {
                        X -> scaleXDiscrete(
                            limits = categories,
                            name = name,
                            breaks = breaks,
                            labels = labels,
                        )
                        Y -> scaleYDiscrete(
                            limits = categories,
                            name = name,
                            breaks = breaks,
                            labels = labels,
                        )
                        else -> TODO("error")
                    }
                }
                is ContinuousPositionalScale<*> -> {
                    when (aes) {
                        X -> scaleXContinuous(limits = limits.toLP(),) // TODO name = axis.name)
                        Y -> scaleYContinuous(limits = limits.toLP(), ) // TODO name = axis.name)
                        else -> TODO()
                    }
                }
                is DefaultScale -> {
                    // TODO add default recognition for guides???
                    return null
                }
                else -> TODO()
            }


        }

        is NonPositionalScale<*, *> -> {
            val legend = legend as? LetsPlotLegend<*, *>
            val name = legend?.name
            val breaks = legend?.breaks
            val labels = legend?.labels
            val legendType = legend?.legendType?.let {
                when(it) {
                    is None -> null
                    is ColorBar -> guideColorbar(
                        barHeight = it.barHeight,
                        barWidth = it.barWidth,
                        nbin = it.nBin
                    )
                    is DiscreteLegend -> guideLegend(
                        nrow = it.nRow,
                        ncol = it.nCol,
                        byRow = it.byRow
                    )
                }
            }

            when(this) {
                is CategoricalNonPositionalScale<*, *> -> {
                    when (aes) {
                        SIZE -> scaleSizeManual(
                            values = values.map { it as Double },
                            name = name,
                            breaks = breaks,
                            labels = labels,
                            guide = legendType
                        )
                        COLOR -> if (values.isEmpty()) {
                            scaleFillDiscrete(
                                name = name,
                                breaks = breaks,
                                labels = labels,
                                guide = legendType
                            )
                        } else {
                            scaleFillManual(
                                values = values.map { (it as StandardColor).description },
                                name = name,
                                breaks = breaks,
                                labels = labels,
                                guide = legendType
                            )
                        }
                        // TODO
                        ALPHA -> scaleAlphaManual(
                            values = values.map { it as Double },
                            name = name,
                            breaks = breaks,
                            labels = labels,
                            guide = legendType
                        ) // TODO
                        SYMBOL -> scaleShapeManual(
                            values = values.map { wrapSymbol(it as Symbol) },
                            name = name,
                            breaks = breaks,
                            labels = labels,
                            guide = legendType
                        )
                        else -> TODO()
                    }
                }
                is ContinuousNonPositionalScale<*, *> -> {
                    when (aes) {
                        SIZE -> scaleSize(
                            limits = domainLimits.toLP(),
                            range = range.toLP(),

                            name = name,
                            breaks = breaks?.map {
                                it as Number },
                            labels = labels,
                            guide = legendType
                        )

                        COLOR, MAPPABLE_BORDER_COLOR -> {
                            val (lowColor, highColor) = range.let {
                                (it?.first as? StandardColor)?.description to (it?.second as? StandardColor)?.description
                            }
                            val limits = domainLimits.toLP() // todo datetime here
                            if (aes == COLOR && geom in fillGeoms){
                                scaleFillContinuous(
                                    low = lowColor,
                                    high = highColor,
                                    limits = limits,
                                    name = name,
                                    breaks = breaks?.map { it as Number },
                                    labels = labels,
                                    guide = legendType
                                )
                            } else {
                                scaleColorContinuous(
                                    low = lowColor,
                                    high = highColor,
                                    limits = limits,
                                    name = name,
                                    breaks = breaks?.map { it as Number },
                                    labels = labels,
                                    guide = legendType
                                )
                            }
                        }   // TODO
                        ALPHA -> scaleAlpha(
                            limits = domainLimits.toLP(),
                            range = range.toLP(),
                            name = name,
                            breaks = breaks?.map { it as Number },
                            labels = labels,
                            guide = legendType
                        ) // TODO
                        SYMBOL -> TODO("cant apply contunuous scale")
                        else -> TODO()
                    }
                }
                is DefaultScale -> {
                    // TODO add default recognition for guides???
                    return null
                }
                else -> TODO()
            }
        }


        is DefaultScale -> {
            // TODO add default recognition for guides???
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
        layer.mappings.forEach { (aes, mapping) ->
            if (mapping is ScalableMapping) {
                mapping.scale.wrap(aes, layer.geom)?.let { buffer += it }
            }
        }
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
