package com.andreikingsley.ggdsl.letsplot.position

import com.andreikingsley.ggdsl.ir.feature.FeatureName
import com.andreikingsley.ggdsl.ir.feature.LayerFeature

abstract class  Position internal constructor(val name: String): LayerFeature {
    override val featureName: FeatureName = POSITION_FEATURE_NAME
    // tODO OBJECTS?
    class Identity: Position("identity")
    class Stack: Position("stack")
    class Dodge(val width: Number? = null): Position("dodge")
    class Jitter(val width: Number? = null, val height: Number? = null): Position("jitter")
    class Nudge(val x: Number? = null, val y: Number? = null): Position("nudge")
    class JitterDodge(
        val dodgeWidth: Number? = null,
        val jitterWidth: Number? = null,
        val jitterHeight: Number? = null,
    ): Position("jitter_dodge")
}
