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

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import de.cacheoverflow.furlang.frontend.FurlangLexer
import de.cacheoverflow.furlang.frontend.FurlangParser
import de.cacheoverflow.furlang.plugin.intellij.FurlangSourceLanguage
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree

class SourceParserDefinition : ParserDefinition {
    init {
        PSIElementTypeFactory.defineLanguageIElementTypes(FurlangSourceLanguage.Companion.INSTANCE, FurlangParser.tokenNames, FurlangParser.ruleNames)
    }
    
    override fun createFile(viewProvider: FileViewProvider): PsiFile = SourcePsiFileBase(viewProvider)
    override fun getStringLiteralElements(): TokenSet = PSIElementTypeFactory.createTokenSet(FurlangSourceLanguage.Companion.INSTANCE,
        FurlangLexer.STRING_MODE_TEXT, FurlangLexer.DOUBLE_QUOTE, FurlangLexer.ML_STRING_BEGIN, FurlangLexer.ML_STRING_END)
    override fun getCommentTokens(): TokenSet = PSIElementTypeFactory.createTokenSet(FurlangSourceLanguage.Companion.INSTANCE,
        FurlangLexer.LINE_COMMENT, FurlangLexer.BLOCK_COMMENT)
    override fun getWhitespaceTokens(): TokenSet = PSIElementTypeFactory.createTokenSet(FurlangSourceLanguage.Companion.INSTANCE, FurlangLexer.WS)
    override fun getFileNodeType(): IFileElementType = IFileElementType(FurlangSourceLanguage.Companion.INSTANCE)
    override fun createLexer(project: Project?): Lexer = ANTLRLexerAdaptor(FurlangSourceLanguage.Companion.INSTANCE, FurlangLexer(null))
    override fun createParser(project: Project?): PsiParser =
        object : ANTLRParserAdaptor(FurlangSourceLanguage.Companion.INSTANCE, FurlangParser(null)) {
            override fun parse(parser: Parser?, root: IElementType?): ParseTree =
                if (root is IFileElementType) (parser as FurlangParser).file() else (parser as FurlangParser).function_declaration()
        }
    
    override fun createElement(node: ASTNode): PsiElement {
        val nodeElementType = node.elementType
        if (nodeElementType is TokenIElementType) return ANTLRPsiNode(node)
        if (nodeElementType !is RuleIElementType) return ANTLRPsiNode(node)
        return ANTLRPsiNode(node) // TODO
    }
}
