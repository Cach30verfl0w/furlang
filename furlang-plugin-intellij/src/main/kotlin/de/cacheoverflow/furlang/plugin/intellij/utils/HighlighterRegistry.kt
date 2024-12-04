package de.cacheoverflow.furlang.plugin.intellij.utils

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.toTypedArray
import kotlin.text.uppercase

internal class HighlighterRegistry(private val prefix: String) {
    private val values: MutableMap<String, TextAttributesKey> = mutableMapOf()
    
    internal val keywordKey: TextAttributesKey = getOrCreate("Keyword", DefaultLanguageHighlighterColors.KEYWORD)
    internal val identifierKey: TextAttributesKey = getOrCreate("Identifier", DefaultLanguageHighlighterColors.IDENTIFIER)
    internal val numberKey: TextAttributesKey = getOrCreate("Number", DefaultLanguageHighlighterColors.NUMBER)
    internal val bracesKey: TextAttributesKey = getOrCreate("Braces", DefaultLanguageHighlighterColors.BRACES)
    internal val stringKey: TextAttributesKey = getOrCreate("String", DefaultLanguageHighlighterColors.STRING)
    internal val blockCommentKey: TextAttributesKey = getOrCreate("Comment", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
    internal val lineCommentKey: TextAttributesKey = getOrCreate("Comment", DefaultLanguageHighlighterColors.LINE_COMMENT)
    
    internal fun getAsDescriptors(): Array<AttributesDescriptor> = values.map { (key, value) -> AttributesDescriptor(key, value) }
        .toTypedArray()
    internal fun getOrCreate(name: String, default: TextAttributesKey): TextAttributesKey {
        if (!values.containsKey(name)) {
            val attributesKey = TextAttributesKey.createTextAttributesKey("${prefix.uppercase()}_${name.uppercase()}", default)
            values[name] = attributesKey
            return attributesKey
        }
        return requireNotNull(values[name])
    }
    
    companion object {
        val SOURCE: HighlighterRegistry = HighlighterRegistry("FURLANG_SOURCE")
    }
}
