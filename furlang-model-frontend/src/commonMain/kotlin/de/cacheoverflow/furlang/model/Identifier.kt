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

package de.cacheoverflow.furlang.model

import de.cacheoverflow.furlang.frontend.FurlangLexer
import org.antlr.v4.kotlinruntime.Lexer

/**
 * This class represents an identifier which is used to parse package names, class names etc. and is used for mangling the types while
 * compilation.
 *
 * @param lexer      Reference to the lexer used
 * @param components Reference to the components
 *
 * @author Cedric Hammes
 * @since  05/12/2024
 */
@Suppress("Unused")
class Identifier(lexer: Lexer, val components: Array<String>) {
    private val delimiter: String = requireNotNull(lexer.vocabulary.getLiteralName(FurlangLexer.Tokens.DOT))
    
    override fun toString(): String = components.joinToString(delimiter)
    fun getName(): String = components[components.size - 1]
    
    companion object {
        /**
         * @param lexer      Reference to the lexer used
         * @param components Reference to the components as string
         * @return           The identifier initialized from string
         *
         * @author Cedric Hammes
         * @since  05/12/2024
         */
        fun fromString(lexer: Lexer, string: String): Identifier = Identifier(lexer, string.split("\\.").toTypedArray())
    }
}
