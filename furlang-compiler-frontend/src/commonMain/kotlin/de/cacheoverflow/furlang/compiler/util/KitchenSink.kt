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

object KitchenSink {
    
    fun replace(string: String, vararg replacements: Any): String {
        val result = StringBuilder(string.length)
        var i = 0
        while (i < string.length) {
            if (string[i] == '{' && i + 1 < string.length && string[i + 1] == '}') {
                result.append(replacements.getOrNull(result.length)?: throw NullPointerException())
                i += 2
            } else {
                result.append(string[i])
                i++
            }
        }
        return result.toString()
    }
}