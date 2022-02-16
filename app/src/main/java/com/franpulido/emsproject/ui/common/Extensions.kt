package com.franpulido.emsproject.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.ActivityOptionsCompat
import kotlin.math.roundToInt

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity)
    startActivity(intentFor<T>(body), options.toBundle())
}

fun Double.round(): Double = (this * 100).roundToInt() / 100.0

val colorsTheme = intArrayOf(
    Color.rgb(77, 182, 172), Color.rgb(1, 135, 134), Color.rgb(187, 134, 252),
    Color.rgb(98, 0, 238)
)
