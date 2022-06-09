package translator

import com.andreikingsley.ggdsl.dsl.scaled
import com.andreikingsley.ggdsl.dsl.source
import com.andreikingsley.ggdsl.ir.Geom
import com.andreikingsley.ggdsl.ir.Layer
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.bindings.NonPositionalSetting
import com.andreikingsley.ggdsl.ir.bindings.*
import com.andreikingsley.ggdsl.ir.data.NamedData
import com.andreikingsley.ggdsl.ir.scale.NonPositionalCategoricalScale
import com.andreikingsley.ggdsl.ir.scale.NonPositionalScale
import com.andreikingsley.ggdsl.ir.scale.PositionalContinuousDefaultScale
import com.andreikingsley.ggdsl.letsplot.position.POSITION_FEATURE_NAME
import com.andreikingsley.ggdsl.letsplot.position.Position
import com.andreikingsley.ggdsl.letsplot.translator.LayerWrapper
import com.andreikingsley.ggdsl.util.color.Color
import jetbrains.letsPlot.intern.GeomKind
import jetbrains.letsPlot.intern.PosKind
import jetbrains.letsPlot.intern.StatKind
import jetbrains.letsPlot.intern.toSpec
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LayerWrapperTest {
    @Test
    fun testSimple() {
        val layer = Layer(
            mapOf(),
            Geom.POINT,
            mapOf(
                COLOR to ScaledUnspecifiedDefaultMapping(
                    COLOR,
                    source<Int>("F").scaled(),
                    typeOf<Int>()
                )
            ),
            mapOf(
                BORDER_COLOR to NonPositionalSetting(
                    BORDER_COLOR,
                    Color.RED
                )
            ),
            mapOf()
        )

        val wrappedLayer = LayerWrapper(layer)
        assertEquals(
            mapOf<String, Any>(
                "mapping" to mapOf(
                    "fill" to "F"
                ),
                "stat" to "identity",
                "data" to mapOf<String, Any>(),
                "shape" to 21.0,
                "color" to "red",
                "position" to "identity",
                "geom" to "point"
            ),
            wrappedLayer.toSpec()
        )
        /*
        println(wrappedLayer.toSpec())

        LayerAssert.assertThat(wrappedLayer)
            .aes("fill", "F")
            .parameter("color", "red")
            .geom()
            .kind(GeomKind.POINT)
            .noMapping()
        LayerAssert.assertThat(wrappedLayer)
            .stat()
            .kind(StatKind.IDENTITY)
        LayerAssert.assertThat(wrappedLayer)
            .position(PosKind.IDENTITY)

         */

    }

    @Test
    fun testComplex() {
        /*
        val dataset: NamedData = mapOf(
            "TIME_T" to listOf(1.0F, 2.0F, 3.0F),
            "VAL_V" to listOf(19921.44, 42423.324, 34242.1987)
        )

         */
        val layer = Layer(
            mapOf(),
            Geom.BAR,
            mapOf(
                X to ScaledUnspecifiedDefaultMapping(
                    X,
                    SourceScaledUnspecifiedDefault(
                        source<Float>("TIME_T")
                    ),
                    typeOf<Float>()
                ),
                Y to ScaledPositionalDefaultMapping(
                    Y,
                    SourceScaledPositionalDefault(
                        source<Double>("VAL_V"),
                        PositionalContinuousDefaultScale
                    ),
                    typeOf<Double>()
                ),
                COLOR to ScaledNonPositionalMapping(
                    COLOR,
                    SourceScaledNonPositional(
                        source<String>("BAFGA"),
                        NonPositionalCategoricalScale(
                            rangeValues = listOf(Color.BLACK, Color.WHITE, Color.GREY)
                        ),
                    ),
                    typeOf<String>(),
                    //typeOf<Color>()
                )
            ),
            mapOf(
                BORDER_COLOR to NonPositionalSetting(
                    BORDER_COLOR,
                    Color.RED
                ),
                WIDTH to NonPositionalSetting(
                    WIDTH,
                    5.0
                ),
            ),
            mapOf(
                POSITION_FEATURE_NAME to Position.Dodge(0.9)
            )
        )

        val wrappedLayer = LayerWrapper(layer)
println(wrappedLayer.toSpec())
        assertEquals(
            mapOf<String, Any>(
                "mapping" to mapOf(
                    "x" to "TIME_T",
                    "y" to "VAL_V",
                    "fill" to "BAFGA",
                ),
                "stat" to "identity",
                "data" to mapOf<String, Any>(),
                "width" to 5.0,
                "color" to "red",
                "position" to mapOf(
                    "name" to "dodge",
                    "width" to 0.9,
                    "kind" to "pos"
                ),
                "geom" to "bar"
            ),
            wrappedLayer.toSpec()
        )

        /*
        LayerAssert.assertThat(wrappedLayer)
            .aes("x", "TIME_T")
            .aes("y", "VAL_V")
            .aes("fill", "BAFGA")
            .parameter("color", "red")
            .parameter("width", 5.0)
            .geom()
            .kind(GeomKind.BAR)
            .noMapping()
        LayerAssert.assertThat(wrappedLayer)
            .stat()
            .kind(StatKind.IDENTITY)

        LayerAssert.assertThat(wrappedLayer)
            .position(PosKind.DODGE)
            .parameter("width", 0.9)


         */
    }
}