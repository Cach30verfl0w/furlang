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

import de.cacheoverflow.furlang.compiler.util.KitchenSink
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.assertEquals

class KitchenSinkTest : ShouldSpec() {
    init {
        should("test getTokensInLineRange") {
            TestHelper.fromParser(
"""pub fun test(value1: i32, value2: i32): i32 {
    let result: i32 = value1 + value2
    result = 1 + 1
    return result
}"""
            ) { stream, parser ->
                val token = TestHelper.getTokenFromLine(stream, 3)
                
                // First test string
                val firstTestString = TestHelper.tokensToText(KitchenSink.getTokensInLineRange(stream, token, 3))
                println("--------------------------\n$firstTestString--------------------------")
                assertEquals("""    let result: i32 = value1 + value2
    result = 1 + 1
    return result""".replace("\n", ""), firstTestString.replace("\n", ""),
                    "Unable to convert token range from line 2 to 4 to string-formatted code")
                
                // Second test string
                val secondTestString = TestHelper.tokensToText(KitchenSink.getTokensInLineRange(stream, token, 1))
                println("--------------------------\n$secondTestString--------------------------")
                assertEquals("""    result = 1 + 1""".replace("\n", ""), secondTestString.replace("\n", ""),
                    "Unable to convert token from line 3 to string-formatted code")
            }
        }
    }
}
