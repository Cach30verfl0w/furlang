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

package de.cacheoverflow.furlang.compiler

import de.cacheoverflow.furlang.frontend.FurlangLexer
import de.cacheoverflow.furlang.frontend.FurlangParser
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import org.antlr.v4.kotlinruntime.Token
import org.antlr.v4.kotlinruntime.TokenStream

object TestHelper {
    fun fromParser(code: String, closure: (TokenStream, FurlangParser) -> Unit) {
        val tokenStream = CommonTokenStream(FurlangLexer(CharStreams.fromString(code)))
        val parser = FurlangParser(tokenStream)
        parser.file()
        closure(tokenStream, parser)
    }
    fun getTokenFromLine(tokenStream: TokenStream, line: Int): Token =
        (0..<tokenStream.size()).map { tokenStream[it] }.first { it.line == line && it.channel == 0 }
    fun tokensToText(tokens: List<Token>): String = tokens.map { if (it.type == FurlangParser.Tokens.EOF) "\n" else it.text }
        .joinToString("") { it?: "" }.let { if (it.endsWith("\n")) it else "$it\n" }
}
