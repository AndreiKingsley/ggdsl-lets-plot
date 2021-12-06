package com.andreikingsley.ggdsl.ir.scale

import com.andreikingsley.ggdsl.ir.scale.guide.Axis
import com.andreikingsley.ggdsl.ir.scale.guide.Legend

sealed interface Scale

sealed class PositionalScale: Scale {
    var axis: Axis? = null
}

sealed class NonPositionalScale: Scale {
    var legend: Legend? = null
}

class CategoricalPositionalScale<T>: PositionalScale() {
    var categories: List<T> = listOf()
}

class ContinuousPositionalScale<T>: PositionalScale() {
    var limits: Pair<T, T>? = null
}

class CategoricalNonPositionalScale<T, R>: PositionalScale() {
    var categories: List<T> = listOf()
    var values: List<R> = listOf()
}

class ContinuousNonPositionalScale<T, R>: PositionalScale() {
    var domainLimits: Pair<T, T>? = null
    var range: Pair<R, R>? = null
}
