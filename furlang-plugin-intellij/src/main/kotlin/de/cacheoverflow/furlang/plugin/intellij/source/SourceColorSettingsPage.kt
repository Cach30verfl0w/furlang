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

package de.cacheoverflow.furlang.plugin.intellij.source

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import de.cacheoverflow.furlang.plugin.intellij.utils.HighlighterRegistry
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

/**
 * @author Cedric Hammes
 * @since  04/12/2024
 */
class SourceColorSettingsPage : ColorSettingsPage {
    override fun getAttributeDescriptors(): Array<out AttributesDescriptor?> = HighlighterRegistry.SOURCE.getAsDescriptors()
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? = null
    override fun getColorDescriptors(): Array<out ColorDescriptor?> = emptyArray()
    override fun getHighlighter(): SyntaxHighlighter = SourceSyntaxHighlighter()
    override fun getDisplayName(): String = "Furlang"
    override fun getIcon(): Icon? = null
    override fun getDemoText(): @NonNls String = """
        /** Block comment **/
        fun test() {
            let mut i = 1
            while (i % 2 == 1) {
                i = i + 1
            }
        }
        
        // Test
        fun function() {
            let mut a = 1
            for (i in 0..20) {
                a = a + i
            }
        }
    """.trimIndent()
}
