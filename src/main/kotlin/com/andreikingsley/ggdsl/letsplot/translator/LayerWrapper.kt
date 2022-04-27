package com.andreikingsley.ggdsl.letsplot.translator

import com.andreikingsley.ggdsl.ir.*
import com.andreikingsley.ggdsl.ir.aes.SYMBOL
import com.andreikingsley.ggdsl.ir.bindings.NonPositionalSetting
import com.andreikingsley.ggdsl.letsplot.position.POSITION_FEATURE_NAME
import jetbrains.letsPlot.Pos
import jetbrains.letsPlot.Stat
import jetbrains.letsPlot.intern.Options

class LayerWrapper internal constructor(private val layer: Layer) :
    jetbrains.letsPlot.intern.layer.LayerBase(
        data = layer.data,
        mapping = Options(layer.mappings.map { (_, mapping) -> mapping.wrap(layer.geom) }.toMap()),
        // todo handle with shapes
        geom = layer.geom.toLPGeom(!(layer.settings.containsKey(SYMBOL) || layer.mappings.containsKey(SYMBOL))),
        stat = Stat.identity,
        position = layer.features[POSITION_FEATURE_NAME]?.wrap() ?: Pos.identity, // TODO(Ok?)
        showLegend = true,
    ) {
    // TODO
    override fun seal() = Options(
        layer.settings.map {
                // TODO(Other settings?)
                (_, setting) -> (setting as NonPositionalSetting<*>).wrap(layer.geom)
        }.toMap()
    )
}
