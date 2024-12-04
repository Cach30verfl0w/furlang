/*
 * Copyright 2024 Cach30verfl0w & contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.cacheoverflow.furlang.plugin.intellij.source

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import de.cacheoverflow.furlang.frontend.FurlangLexer
import de.cacheoverflow.furlang.plugin.intellij.FurlangSourceLanguage
import de.cacheoverflow.furlang.plugin.intellij.utils.HighlighterRegistry
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class SourceSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = ANTLRLexerAdaptor(FurlangSourceLanguage.INSTANCE, FurlangLexer(null))
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        if (tokenType !is TokenIElementType) return emptyArray()
        return when (tokenType.antlrTokenType) {
            FurlangLexer.KW_PUBLIC -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_PROTECTED -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_PRIVATE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_INTERNAL -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_EXTERN -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_CONST -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_VARARG -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_IMPORT -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_OPEN -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_FUNCTION -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_OPERATOR -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_ABSTRACT -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_CLASS -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_INTERFACE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_ANNOTATION -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_IN -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_AS -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_IS -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_LET -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_MUTABLE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_WHEN -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_DO -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_FOR -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_IF -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_ELSE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_RETURN -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_NULL -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_WHILE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_TRUE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_FALSE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_BREAK -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.KW_CONTINUE -> arrayOf(HighlighterRegistry.Companion.SOURCE.keywordKey)
            FurlangLexer.IDENTIFIER -> arrayOf(HighlighterRegistry.Companion.SOURCE.identifierKey)
            FurlangLexer.INTEGER, FurlangLexer.FLOAT -> arrayOf(HighlighterRegistry.Companion.SOURCE.numberKey)
            FurlangLexer.L_BRACE, FurlangLexer.R_BRACE -> arrayOf(HighlighterRegistry.Companion.SOURCE.bracesKey)
            FurlangLexer.SIGNED_INT, FurlangLexer.UNSIGNED_INT, FurlangLexer.BOOLEAN, FurlangLexer.STRING ->
                arrayOf(HighlighterRegistry.Companion.SOURCE.identifierKey)
            FurlangLexer.DOUBLE_QUOTE, FurlangLexer.STRING_MODE_TEXT -> arrayOf(HighlighterRegistry.Companion.SOURCE.stringKey)
            FurlangLexer.ML_STRING_BEGIN, FurlangLexer.ML_STRING_END, FurlangLexer.ML_STRING_MODE_TEXT ->
                arrayOf(HighlighterRegistry.Companion.SOURCE.stringKey)
            FurlangLexer.LINE_COMMENT -> arrayOf(HighlighterRegistry.Companion.SOURCE.lineCommentKey)
            FurlangLexer.BLOCK_COMMENT -> arrayOf(HighlighterRegistry.Companion.SOURCE.blockCommentKey)
            else -> emptyArray()
        }
    }
}
