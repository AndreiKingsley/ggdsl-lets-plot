package com.andreikingsley.ggdsl.letsplot.position

import com.andreikingsley.ggdsl.dsl.LayerContext

// todo add backing property?
/**
 * Position adjustment of this layer.
 *
 * @see [Position]
 */
var LayerContext.position: Position
    get() = Position.Identity
    set(pos) {
        features[POSITION_FEATURE_NAME] = pos
    }
