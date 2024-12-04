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

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import de.cacheoverflow.furlang.plugin.intellij.FurlangSourceLanguage
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class FurlangSourceFileType : LanguageFileType(FurlangSourceLanguage.Companion.INSTANCE) {
    override fun getName(): @NonNls String = "Furlang"
    override fun getDescription(): @NlsContexts.Label String = "File for Furlang source code"
    override fun getDefaultExtension(): String = FILE_EXTENSION
    override fun getIcon(): Icon? = null
    
    companion object {
        val INSTANCE: FurlangSourceFileType = FurlangSourceFileType()
        const val FILE_EXTENSION: String = "arf"
    }
}
