package com.andreikingsley.ggdsl.letsplot

import com.andreikingsley.ggdsl.ir.aes.MappableNonPositionalAes
import com.andreikingsley.ggdsl.ir.aes.NonPositionalAes

// fix to positional non scalable???? SubPoistional
val LOWER = MappableNonPositionalAes<Double>("lower")
val UPPER = MappableNonPositionalAes<Double>("upper")
val MIDDLE = MappableNonPositionalAes<Double>("middle")
val Y_MIN = MappableNonPositionalAes<Double>("ymin")
val Y_MAX = MappableNonPositionalAes<Double>("ymax")
val FATTEN = NonPositionalAes<Double>("fatten")