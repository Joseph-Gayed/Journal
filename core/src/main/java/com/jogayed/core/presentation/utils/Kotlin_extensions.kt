package com.jogayed.core.presentation.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.reFormatDecimalPlaces(format: String = "###,###.###"): Double = DecimalFormat(
    format,
    DecimalFormatSymbols(Locale.ENGLISH)
).format(this).toDouble()


