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

package de.cacheoverflow.furlang.model.node.scope

import de.cacheoverflow.furlang.model.Identifier
import de.cacheoverflow.furlang.model.Modifier
import de.cacheoverflow.furlang.model.node.AnnotationNode
import de.cacheoverflow.furlang.model.node.scope.ScopeNode
import org.antlr.v4.kotlinruntime.Lexer

/**
 * @author Cedric Hammes
 * @since  05/12/2024
 */
class FileNode(
    override val name: Identifier,
    override val parent: Node?,
    override val modifier: Modifier,
    override val annotations: List<AnnotationNode>
) : ScopeNode {
    override val children: MutableList<Node> = ArrayList()
    
    companion object {
        fun new(lexer: Lexer, fileName: String): FileNode = FileNode(Identifier(lexer, arrayOf(fileName)), null, Modifier.NONE, emptyList())
    }
}
