package com.andreikingsley.ggdsl.ir.aes

sealed class Aes(val name: String)

class PositionalAes(name: String): Aes(name)

class NonPositionalAes<T>(name: String): Aes(name)
