package de.cacheoverflow.furlang.model.node

import de.cacheoverflow.furlang.model.Modifier

/**
 * @author Cedric Hammes
 * @since  02/12/2024
 */
interface ScopeNode : Node {
    val modifier: Modifier
    val annotations: List<AnnotationNode>
    val children: List<Node>
    val parent: Node?
}
