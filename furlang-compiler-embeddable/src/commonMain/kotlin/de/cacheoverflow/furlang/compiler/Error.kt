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

import io.github.oshai.kotlinlogging.KLogger

/**
 * @param code     The numeric part of the error code, a.e. error 1000 for E1000
 * @param message  The message shown to the user when emitting this error
 * @param severity The severity of the error.
 *
 * @author Cedric Hammes
 * @since  06/12/2024
 */
@ConsistentCopyVisibility
data class Error private constructor(val code: UShort, val message: String, val severity: Severity, val interruptsCompilation: Boolean) {
    init {
        require(message.isNotBlank()) { "Error messages cannot be blank" }
    }
    
    override fun toString(): String = "${severity.short}${code}"
    
    /**
     * @param short The short literal of the error
     * @param long  The complete literal of the error
     *
     * @author Cedric Hammes
     * @since  06/12/2024
     */
    enum class Severity(internal val long: String, internal val short: String, internal val loggerClosure: KLogger.(() -> Any?) -> Unit) {
        ERROR("error", "E", { error(it) }),
        WARNING("warning", "W", { warn(it) })
    }
    
    companion object {
        fun warning(code: UShort, message: String, interruptsCompilation: Boolean = false): Error =
            Error(code, message, Severity.WARNING, interruptsCompilation)
        fun error(code: UShort, message: String, interruptsCompilation: Boolean = false): Error =
            Error(code, message, Severity.ERROR, interruptsCompilation)
        
        val TEST: Error = error(1000U, "Test")
    }
}
