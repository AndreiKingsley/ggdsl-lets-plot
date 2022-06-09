package dsl

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Layout
import com.andreikingsley.ggdsl.ir.Plot
import com.andreikingsley.ggdsl.ir.data.DataSource
import com.andreikingsley.ggdsl.letsplot.facet.*
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

class FacetTest {
    @Test
    fun testSimpleFacet() {
        val plot = plot {
            facetGrid {
                x(source<String>("xSrc"))
            }
        }
        assertEquals(
            Plot(
                mapOf(),
                listOf(),
                Layout(),
                mapOf(
                    FacetGridFeature.FEATURE_NAME to FacetGridFeature().apply {
                        mappings[FACET_X] = DataSource<String>("xSrc", typeOf<String>())
                    }
                )
            ),
            plot
        )
    }

    @Test
    fun testComplexFacet() {
        val plot = plot {
            facetGrid {
                x(source<String>("xArg"))
                y(source<Int>("yArg"))
                xOrder = OrderDirection.ASCENDING
                yOrder = OrderDirection.DESCENDING
            }
        }
        assertEquals(
            Plot(
                mapOf(),
                listOf(),
                Layout(),
                mapOf(
                    FacetGridFeature.FEATURE_NAME to FacetGridFeature().apply {
                        mappings[FACET_X] = DataSource<String>("xArg", typeOf<String>())
                        mappings[FACET_Y] = DataSource<Int>("yArg", typeOf<Int>())
                        xOrder = OrderDirection.ASCENDING
                        yOrder = OrderDirection.DESCENDING
                    }
                )
            ),
            plot
        )
    }
}