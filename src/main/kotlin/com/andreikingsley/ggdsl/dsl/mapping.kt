package com.andreikingsley.ggdsl.dsl

import com.andreikingsley.ggdsl.ir.aes.*

class PositionalMapping<T>(
    val aes: PositionalAes,
    val source: DataSource<T>
)

class NonPositionalMapping<R, T>(
    val aes: NonPositionalAes<R>,
    val source: DataSource<T>
)