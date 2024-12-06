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

import de.cacheoverflow.furlang.model.node.Identifier
import de.cacheoverflow.furlang.model.node.Modifier
import de.cacheoverflow.furlang.model.node.AnnotationNode
import de.cacheoverflow.furlang.model.util.Named

/**
 * @author Cedric Hammes
 * @since  02/12/2024
 */
interface Scope : Named {
    val parent: Scope?
    val children: List<Named>
    val modifier: Modifier
    val annotations: List<AnnotationNode>
    val imports: List<Identifier>
}