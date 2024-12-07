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

import com.github.ajalt.mordant.terminal.Terminal
import de.cacheoverflow.furlang.compiler.util.KitchenSink
import de.cacheoverflow.furlang.compiler.util.TokenHelper
import io.github.oshai.kotlinlogging.DelegatingKLogger
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KLoggingEventBuilder
import io.github.oshai.kotlinlogging.Level
import io.github.oshai.kotlinlogging.Marker
import org.antlr.v4.kotlinruntime.Parser
import org.antlr.v4.kotlinruntime.Token

/**
 * @author Cedric Hammes
 * @since  06/12/2024
 */
class CompilerContext(private val parser: Parser?, logger: KLogger) {
    private val terminal: Terminal = Terminal()
    val logger: KLogger = KotlinLoggerWrapper(logger, terminal)
    
    /**
     * This function emits the specified error with the specified optional information to the user. It optionally contains information about
     * the source code where the error/warning was emitted.
     *
     * @param error     Reference to the error emitted to the context
     * @param token     Reference to the token where the error was emitted
     * @param lineCount Count of the lines of the code displayed
     * @param info      Info replacements for the error message
     *
     * @author Cedric Hammes
     * @since  06/12/2024
     */
    fun emitError(error: Error, token: Token? = null, lineCount: Int = 5, info: Array<Any> = emptyArray<Any>()) {
        fun emitString(message: String): Unit = error.severity.loggerClosure.invoke(logger) { message }
        
        val tokens = token?.let { TokenHelper.findTokensInLineRange(requireNotNull(parser).tokenStream, it, lineCount) }
        val markedCode = token?.let { TokenHelper.visualizeTokensForError(requireNotNull(tokens), it) }
        val message = KitchenSink.replace(error.message, info)
        terminal.println((markedCode?: emptyList()).joinToString("\n"))
    }
    
    /**
     * This class is implementing a logger wrapper for the internal logger of the [CompilerContext].
     *
     * @author Cedric Hammes
     * @since  07/12/2024
     */
    internal class KotlinLoggerWrapper(
        override val underlyingLogger: KLogger,
        private val terminal: Terminal
    ) : KLogger by underlyingLogger, DelegatingKLogger<KLogger> {
        override fun at(level: Level, marker: Marker?, block: KLoggingEventBuilder.() -> Unit) {
            val eventBuilder = KLoggingEventBuilder().apply(block)
            terminal.println(eventBuilder.message)
        }
    }
}
