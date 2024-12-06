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

package de.cacheoverflow.furlang.model.node

import de.cacheoverflow.furlang.frontend.FurlangParser
import de.cacheoverflow.furlang.frontend.FurlangParserBaseVisitor
import de.cacheoverflow.furlang.model.node.scope.FileNode
import de.cacheoverflow.furlang.model.node.scope.Scope
import de.cacheoverflow.furlang.compiler.util.Stack

class FileVisitor(private val fileNode: FileNode) : FurlangParserBaseVisitor<Unit>() {
    private val scopeStack: Stack<Scope> = Stack<Scope>().also { it.push(fileNode) }
    override fun defaultResult() = Unit
    
    override fun visitFunctionDeclaration(declarationContext: FurlangParser.FunctionDeclarationContext) {
        // TODO: Throw error if constructor function declaration and no class scope
        
    }
}