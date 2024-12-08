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

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.parameters.options.NullableOption
import com.github.ajalt.clikt.parameters.options.RawOption
import com.github.ajalt.clikt.parameters.options.convert
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

fun RawOption.path(
    mustNotExists: Boolean = false,
    mustExist: Boolean = false,
    canBeFile: Boolean = false,
    canBeFolder: Boolean = false,
    fileSystem: FileSystem = SystemFileSystem
): NullableOption<Path, Path> = convert({ localization.pathMetavar() }, CompletionCandidates.Path) { value ->
    return@convert with(context.localization) {
        Path(value).also { path ->
            if (mustExist && !fileSystem.exists(path)) fail(pathDoesNotExist(name, path.toString()))
            if (mustNotExists && fileSystem.exists(path)) fail("$name \"$path\" does exist.")
            if (!canBeFile && fileSystem.metadataOrNull(path)?.isRegularFile == true) fail(pathIsFile(name, path.toString()))
            if (!canBeFolder && fileSystem.metadataOrNull(path)?.isDirectory == true) fail(pathIsDirectory(name, path.toString()))
        }
    }
}
