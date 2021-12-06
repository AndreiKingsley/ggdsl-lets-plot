package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.*
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.scale.*
import jetbrains.letsPlot.Pos
import jetbrains.letsPlot.Stat
import jetbrains.letsPlot.intern.Options
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.*

class LayerWrapper(val layer: Layer) :
    jetbrains.letsPlot.intern.layer.LayerBase(
        data = layer.data,
        mapping = Options(layer.mappings.map { (aes, id) -> aes.toLPName(layer.geom) to id }.toMap()),
        geom = layer.geom.toLPGeom(),
        stat = Stat.identity,
        position = Pos.dodge, // TODO
        showLegend = true,
    ) {
    override fun seal() = Options(layer.settings.map { (aes, id) -> aes.toLPName(layer.geom) to id }.toMap())
}

// TODO
fun Aes.toLPName(geom: Geom): String {
    if (geom == Geom.BAR && name == "color") {
        return "fill"
    }
    return name
}

fun Geom?.toLPGeom(): jetbrains.letsPlot.intern.layer.GeomOptions {
    return when (this!!) {
        Geom.POINT -> jetbrains.letsPlot.Geom.point()
        Geom.BAR -> jetbrains.letsPlot.Geom.bar()
        Geom.LINE -> jetbrains.letsPlot.Geom.line()
        else -> TODO()
    }
}

fun Scale.wrap(aes: Aes, geom: Geom): jetbrains.letsPlot.intern.Scale {

    return when (this) {
        is CategoricalPositionalScale<*> -> {
            when (aes) {
                X -> scaleXDiscrete(limits = categories)
                Y -> scaleYDiscrete(limits = categories)
                else -> TODO()
            }
        }
        is ContinuousPositionalScale<*> -> {
            when (aes) {
                X -> scaleXContinuous(limits = limits.toLP())
                Y -> scaleYContinuous(limits = limits.toLP())
                else -> TODO()
            }
        }
        is CategoricalNonPositionalScale<*, *> -> {
            when (aes) {
                SIZE -> scaleSizeManual(values = values.map { it as Number }) // TODO
                COLOR -> when (geom) {
                    Geom.BAR -> if (values.isEmpty()) {
                        scaleFillDiscrete()
                    } else scaleFillManual(values = values) // TODO
                    else -> if (values.isEmpty()) {
                        scaleColorDiscrete()
                    } else scaleColorManual(values = values) // TODO
                } // TODO

                else -> TODO()
            }
        }
        is ContinuousNonPositionalScale<*, *> -> {
            when (aes) {
                SIZE -> scaleSize(limits = domainLimits.toLP(), range = range.toLP()) // TODO
                COLOR -> when (geom) {
                    Geom.BAR -> scaleFillContinuous(
                        low = range?.first.toString(),
                        high = range?.second.toString(),
                        limits = domainLimits.toLP()
                    )
                    else -> scaleColorContinuous(
                        low = range?.first.toString(),
                        high = range?.second.toString(),
                        limits = domainLimits.toLP()
                    )
                }  // TODO
                else -> TODO()
            }
        }
        else -> TODO()
    }

}

// TODO
fun Pair<Any, Any>?.toLP() = this?.let { (it.first as Number) to (it.second as Number) }

fun Plot.toPlot(): jetbrains.letsPlot.intern.Plot {
    // TODO
    return layers.fold(letsPlot(dataset)) { plot, layer ->
        var buffer = plot + LayerWrapper(layer)
        layer.scales.forEach { (aes, scale) -> buffer += scale.wrap(aes, layer.geom) }
        buffer
    }
}
