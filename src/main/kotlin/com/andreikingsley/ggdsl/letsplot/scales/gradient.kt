package com.andreikingsley.ggdsl.letsplot.scales

import com.andreikingsley.ggdsl.dsl.NonPositionalMapping
import com.andreikingsley.ggdsl.ir.scale.*
import com.andreikingsley.ggdsl.util.Color
import jetbrains.letsPlot.scale.scaleColorGradient
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class ScaleGradient<DomainType>(val domainType: KType) : CustomScale{
    var domainLimits: Pair<DomainType, DomainType>? = null
    var range: Pair<Color, Color>? = null
}

// TODO add BaseContext
inline infix fun <reified DomainType> NonPositionalMapping<DomainType, Color>.scaleGradient(
    block: ScaleGradient<DomainType>.() -> Unit
): ScaleGradient<DomainType>{
    TODO()
    //scaleColorGradient()
    return ScaleGradient<DomainType>(typeOf<DomainType>()).apply(block)
}
