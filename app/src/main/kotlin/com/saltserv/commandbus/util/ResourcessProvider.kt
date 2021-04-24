package com.saltserv.commandbus.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat

interface ResourcesProvider {

    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, vararg args: Any): String

    fun getStringArray(@ArrayRes arrayId: Int): Array<String>

    fun getHtml(@StringRes stringId: Int, vararg args: Any): CharSequence

    fun getInt(@IntegerRes integerId: Int): Int

    fun getColor(@ColorRes colorId: Int): Int

    fun getPxDimenAttribute(@AttrRes attrId: Int): Int

    fun getDimen(@DimenRes dimenId: Int): Int
}

class ResourcesProviderImpl(private val context: Context) : ResourcesProvider {

    override fun getString(stringId: Int): String = context.getString(stringId)

    override fun getString(stringId: Int, vararg args: Any): String =
        context.getString(stringId, *args)

    override fun getStringArray(arrayId: Int): Array<String> =
        context.resources.getStringArray(arrayId)

    override fun getHtml(stringId: Int, vararg args: Any) = context
        .getString(stringId, *args)
        .let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }

    override fun getInt(integerId: Int): Int = context.resources.getInteger(integerId)

    override fun getColor(colorId: Int): Int =
        ResourcesCompat.getColor(context.resources, colorId, null)

    override fun getPxDimenAttribute(attrId: Int): Int {
        val tv = TypedValue()
        context.theme.resolveAttribute(attrId, tv, true)
        return context.resources.getDimensionPixelSize(tv.resourceId)
    }

    override fun getDimen(dimenId: Int) = context.resources.getDimensionPixelSize(dimenId)
}