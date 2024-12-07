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

package de.cacheoverflow.furlang.compiler.executable

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands

internal class FurlangCompilerCommand : CliktCommand() {
    override fun help(context: Context): String = """
    This application is the compiler toolchain for the Furlang programming
    language that is used to compile furlang source code into binaries with
    LLVM. It can be used in combination with a Gradle project to compile a
    source tree into a single binary.
    """.trimIndent()
    
    override fun run() = Unit
}

fun main(args: Array<String>) = FurlangCompilerCommand().subcommands(CompileLLVMCommand()).main(args)
