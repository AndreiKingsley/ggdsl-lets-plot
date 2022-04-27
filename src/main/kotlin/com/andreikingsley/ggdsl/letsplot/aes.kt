package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.aes.MappableNonPositionalAes
import com.andreikingsley.ggdsl.ir.aes.NonPositionalAes
import com.andreikingsley.ggdsl.util.color.Color

// fix to positional non scalable???? SubPoistional
val LOWER = MappableNonPositionalAes<Double>("lower")
val UPPER = MappableNonPositionalAes<Double>("upper")
val MIDDLE = MappableNonPositionalAes<Double>("middle")
val Y_MIN = MappableNonPositionalAes<Double>("ymin")
val Y_MAX = MappableNonPositionalAes<Double>("ymax")
val FATTEN = NonPositionalAes<Double>("fatten")

// todo
val MAPPABLE_BORDER_COLOR = MappableNonPositionalAes<Color>("border_color")