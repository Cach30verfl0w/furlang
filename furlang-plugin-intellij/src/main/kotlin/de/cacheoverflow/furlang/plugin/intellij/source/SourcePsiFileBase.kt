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

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import de.cacheoverflow.furlang.plugin.intellij.FurlangSourceLanguage
import org.antlr.intellij.adaptor.SymtabUtils
import org.antlr.intellij.adaptor.psi.ScopeNode

class SourcePsiFileBase(fileViewProvider: FileViewProvider) : PsiFileBase(fileViewProvider, FurlangSourceLanguage.INSTANCE), ScopeNode {
    override fun getFileType(): FileType = FurlangSourceFileType.INSTANCE
    override fun getContext(): ScopeNode? = null
    override fun resolve(element: PsiNamedElement?): PsiElement? =
        SymtabUtils.resolve(this, FurlangSourceLanguage.INSTANCE, element, "/script/vardef/IDENTIFIER")
}
