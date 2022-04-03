package com.andreikingsley.ggdsl.letsplot.scales

import com.andreikingsley.ggdsl.dsl.NonPositionalMapping
import com.andreikingsley.ggdsl.ir.scale.*
import com.andreikingsley.ggdsl.util.color.*
import jetbrains.letsPlot.scale.scaleColorGradient
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class ScaleGradient<DomainType>(val domainType: KType) : CustomScale{
    var domainLimits: Pair<DomainType, DomainType>? = null
    var range: Pair<StandardColor, StandardColor>? = null
}

// TODO add BaseContext context
inline infix fun <reified DomainType> NonPositionalMapping<DomainType, StandardColor>.scaleGradient(
    block: ScaleGradient<DomainType>.() -> Unit
): ScaleGradient<DomainType>{
    TODO()
    //scaleColorGradient()
    return ScaleGradient<DomainType>(typeOf<DomainType>()).apply(block)
}
