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

package de.cacheoverflow.furlang.compiler.util

import org.antlr.v4.kotlinruntime.Token
import org.antlr.v4.kotlinruntime.TokenStream
import kotlin.math.floor

object TokenHelper {
    fun findTokensInLineRange(stream: TokenStream, token: Token, lineCount: Int): List<Token> {
        val halfLineCount = if (lineCount > 1) floor(lineCount / 2f).toInt() else 0
        val lineRange = (token.line - halfLineCount)..(token.line + halfLineCount)
        
        // First, we need to get to the start
        var index = token.tokenIndex
        while (index > 0 && stream[index].line in lineRange) {
            index--
        }
        index++
        
        // After, we need to collect all tokens in range
        val tokenList: MutableList<Token> = mutableListOf()
        val maxIndex = stream.size() - 1
        while (index > 0 && index < maxIndex) {
            val token = stream[index]
            if (token.line !in lineRange)
                break
            tokenList.add(token)
            index++
        }
        
        // Return list
        return tokenList
    }
    
    fun visualizeTokens(tokens: List<Token>, markedToken: Token): String {
        val stringBuilder = StringBuilder()
        for (token in tokens) {
            stringBuilder.append(token)
        }
        return stringBuilder.toString()
    }
}
