package dsl

import com.andreikingsley.ggdsl.dsl.*
import com.andreikingsley.ggdsl.ir.Layer
import com.andreikingsley.ggdsl.ir.Layout
import com.andreikingsley.ggdsl.ir.Plot
import com.andreikingsley.ggdsl.ir.aes.*
import com.andreikingsley.ggdsl.ir.bindings.*
import com.andreikingsley.ggdsl.ir.scale.NonPositionalCategoricalScale
import com.andreikingsley.ggdsl.ir.scale.PositionalContinuousDefaultScale
import com.andreikingsley.ggdsl.letsplot.layers.AREA
import com.andreikingsley.ggdsl.letsplot.layers.area
import com.andreikingsley.ggdsl.util.color.Color
import kotlin.reflect.typeOf
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LayersTest {
    @Test
    fun testArea(){
        val time = source<Int>("time")
        val type = source<String>("type")
        val plot = plot {
            area {
                y(time.scaled(continuousPos()))
                color(type.scaled(
                    categorical(
                        rangeValues = listOf(Color.RED, Color.BLUE)
                    )
                ))

                alpha(0.7)
                borderWidth(2.0)
            }
        }
        assertEquals(
            Plot(
                mapOf(),
                listOf(
                    Layer(
                        mapOf(),
                        AREA,
                        mapOf(
                            Y to ScaledPositionalDefaultMapping(
                                Y, SourceScaledPositionalDefault(
                                    time,
                                    PositionalContinuousDefaultScale
                                ), typeOf<Int>()
                            ),
                            COLOR to ScaledNonPositionalMapping(
                                COLOR, SourceScaledNonPositional(
                                    type,
                                    NonPositionalCategoricalScale(
                                        rangeValues = listOf(Color.RED, Color.BLUE)
                                    )
                                ),
                                typeOf<String>(),
                              //  typeOf<Color>()
                            )
                        ),
                        mapOf(
                            ALPHA to NonPositionalSetting(
                                ALPHA,
                                0.7
                            ),
                            BORDER_WIDTH to NonPositionalSetting(
                                BORDER_WIDTH,
                                2.0
                            )
                        ),
                        mapOf()
                    )
                ),
                Layout(),
                mapOf()
            ),
            plot
        )
    }
    // todo others???
}