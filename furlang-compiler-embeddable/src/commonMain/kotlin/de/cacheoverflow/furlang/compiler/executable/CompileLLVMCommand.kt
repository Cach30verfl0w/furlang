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
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import kotlinx.io.files.Path

internal class CompileLLVMCommand : CliktCommand(name = "compile-llvm") {
    override fun help(context: Context): String = "Compiling a Furlang source tree/project with LLVM into a native executable binary"
    
    val buildFolder: Path by option(help = "Folder used for storing the compiler's build output")
        .path(mustNotExists = true, canBeFolder = true).required()
    val sourceFolder: Path by option(help = "Folder for the source of the project")
        .path(mustExist = true, canBeFolder = true).required()
    
    override fun run() {
    }
}
