package com.andreikingsley.ggdsl.letsplot.position

import com.andreikingsley.ggdsl.ir.feature.FeatureName
import com.andreikingsley.ggdsl.ir.feature.LayerFeature

sealed class Position private constructor(val name: String) : LayerFeature {
    override val featureName: FeatureName = POSITION_FEATURE_NAME

    // tODO OBJECTS?
    object Identity : Position("identity")
    object Stack : Position("stack")
    data class Dodge(val width: Number? = null) : Position("dodge")
    data class Jitter(val width: Number? = null, val height: Number? = null)
        : Position("jitter")
    data class Nudge(val x: Number? = null, val y: Number? = null)
        : Position("nudge")
    data class JitterDodge(
        val dodgeWidth: Number? = null,
        val jitterWidth: Number? = null,
        val jitterHeight: Number? = null,
    ) : Position("jitter_dodge")
}
