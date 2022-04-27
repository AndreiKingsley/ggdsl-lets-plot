package com.andreikingsley.ggdsl.letsplot.facet

import com.andreikingsley.ggdsl.ir.data.DataSource
import com.andreikingsley.ggdsl.dsl.PlotContext
import com.andreikingsley.ggdsl.ir.feature.FeatureName
import com.andreikingsley.ggdsl.ir.feature.PlotFeature

class FacetAes(val name: String)

val FACET_X = FacetAes("x")
val FACET_Y = FacetAes("y")

class OrderDirection private constructor(val value: Int){
    companion object {
        val ASCENDING = OrderDirection(1)
        val DESCENDING = OrderDirection(-1)
    }
}

class FacetGridFeature : PlotFeature {
    override val featureName: FeatureName = FACET_GRID_FEATURE
    val mappings: MutableMap<FacetAes, DataSource<Any>> = mutableMapOf()
    val x = FACET_X
    val y = FACET_Y

    var xOrder: OrderDirection = OrderDirection.ASCENDING
    var yOrder: OrderDirection = OrderDirection.ASCENDING
    /* TODO
    val xFormat: String? = null
    val yFormat: String? = null

     */

    inline operator fun <reified DomainType : Any> FacetAes.invoke(dataSource: DataSource<DomainType>) {
        mappings[this] = dataSource
    }
}

val FACET_GRID_FEATURE = FeatureName("FACET_GRID_FEATURE")

fun PlotContext.facetGrid(block: FacetGridFeature.() -> Unit) {
     features[FACET_GRID_FEATURE] = FacetGridFeature().apply(block)
}
