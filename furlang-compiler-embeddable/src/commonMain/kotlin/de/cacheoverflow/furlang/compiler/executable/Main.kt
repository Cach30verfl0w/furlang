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
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import io.github.oshai.kotlinlogging.Level
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.io.files.Path

@OptIn(ExperimentalForeignApi::class)
internal class FurlangCompilerCommand : CliktCommand() {
    override fun help(context: Context): String = """
    This tool is the CLI wrapper for the Furlang compiler which compiles Furlang project source
    tree into LLVM binaries for the specified target triple. This tool is mostly used by the Gradle
    Furlang Gradle plugin to make the compiler usage more developer-friendly.
    """.trimIndent()
    
    private val llvmDefaultTarget: String = "ea"
    
    // Logging
    val logLevel: Level by option(help = "Max log level printed out by the compiler (default: INFO)").enum<Level>().default(Level.INFO)
    val logFile: Path? by option(help = "Path to the log output file (optional)").path(mustNotExists = true)
    
    // Source tree
    val sourceFolder: Path by option(help = "Folder containing the source tree").path(mustExist = true, canBeFolder = true).required()
    val buildFolder: Path by option(help = "Folder for the binary output").path(mustNotExists = true).required()
    
    // LLVM options TODO: set default target own platform
    val llvmTarget: String by option(help = "Target for the LLVM compilation (default: $llvmDefaultTarget)").default(llvmDefaultTarget)
    
    override fun run() {
    }
}

fun main(args: Array<String>) = FurlangCompilerCommand().main(args)
