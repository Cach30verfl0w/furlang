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

package de.cacheoverflow.furlang.model

/**
 * Represents a set of modifiers using a bitmask representation. Modifiers are used to define access levels (e.g., `PUBLIC`, `PRIVATE`) or
 * other characteristics (e.g., `CONST`, `OPERATOR`) for elements such as classes, methods, or properties. Each modifier is represented as
 * a unique bit in an unsigned 16-bit integer ([UShort]).
 *
 * @param raw The raw modifier value represented as a [UShort]
 *
 * @author Cedric Hammes
 * @since  02/12/2024
 */
value class Modifier private constructor(val raw: UShort) {
    /**
     * Ensures that only a single access modifier is set. Access modifiers include `PUBLIC`, `PROTECTED`, `PRIVATE`, and `INTERNAL`. If
     * multiple access modifiers are found, an [IllegalArgumentException] is thrown.
     *
     * @throws IllegalArgumentException if multiple access modifiers are set
     *
     * @author Cedric Hammes
     * @since  02/12/2024
     */
    fun isValidAccess(): Boolean = (raw and ACCESS_MODIFIERS).countOneBits() <= 1
    
    /**
     * Combines the current modifier with another modifier.
     *
     * @param modifier The modifier to add
     * @return A new [Modifier] instance with the combined modifiers
     *
     * @author Cedric Hammes
     * @since  02/12/2024
     */
    operator fun plus(modifier: Modifier): Modifier = Modifier(raw or modifier.raw)
    
    /**
     * Removes the specified modifier from the current set of modifiers.
     *
     * @param modifier The modifier to remove
     * @return A new [Modifier] instance with the specified modifier removed
     *
     * @author Cedric Hammes
     * @since  02/12/2024
     */
    operator fun minus(modifier: Modifier): Modifier = Modifier(raw and modifier.raw.inv())
    
    /**
     * Checks if the current modifier contains the specified modifier.
     *
     * @param modifier The modifier to check for
     * @return `true` if the current modifier contains all bits of the specified modifier, `false` otherwise
     *
     * @author Cedric Hammes
     * @since  02/12/2024
     */
    operator fun contains(modifier: Modifier): Boolean = (raw and modifier.raw) == modifier.raw
    
    companion object {
        private const val ACCESS_MODIFIERS: UShort = 0b0000_0000_0000_1111U
        
        /**
         * Represents the `public` access modifier. This modifier allows the marked element to be accessed from any other code, regardless
         * of package or module boundaries.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val PUBLIC: Modifier = Modifier(0b0000_0000_0000_0001U)
        
        /**
         * Represents the `protected` access modifier. This modifier restricts access to the defining class and its subclasses. It cannot be
         * applied to top-level declarations.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val PROTECTED: Modifier = Modifier(0b0000_0000_0000_0010U)
        
        /**
         * Represents the `private` access modifier. This modifier restricts access to the defining class or file. It ensures that the
         * marked element is not accessible from other classes or files, enforcing strong encapsulation.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val PRIVATE: Modifier = Modifier(0b0000_0000_0000_0100U)
        
        /**
         * Represents the `internal` access modifier. This modifier restricts access to the same module. Elements marked as `internal` are
         * accessible within the same module but not outside it.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val INTERNAL: Modifier = Modifier(0b0000_0000_0000_1000U)
        
        /**
         * Represents the `extern` declaration modifier. This modifier marks a declaration as being defined outside the project codebase,
         * typically in native or external environments.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val EXTERN: Modifier = Modifier(0b0000_0000_0001_0000U)
        
        /**
         * Represents the `const` modifier. This modifier marks a function for the compiler that it can be compile-time executed. It can
         * only be applied to `val` properties of primitive types or strings.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val CONST: Modifier = Modifier(0b0000_0000_0010_0000U)
        
        /**
         * Represents the `vararg` modifier for function or type parameters. This modifier allows a function to accept a variable number of
         * arguments. These arguments are passed as an array.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val VARARG: Modifier = Modifier(0b0000_0000_0100_0000U)
        
        /**
         * Represents the `operator` modifier. This modifier allows a function to be used with the operator overloading syntax (e.g., `+`,
         * `-`, `[]`, etc.).
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val OPERATOR: Modifier = Modifier(0b0000_0000_1000_0000U)
        
        /**
         * Represents the `abstract` modifier. This modifier marks a class or function as abstract, meaning it cannot be instantiated
         * directly and may contain abstract members that must be implemented by subclasses.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val ABSTRACT: Modifier = Modifier(0b0000_0001_0000_0000U)
        
        /**
         * Represents the `open` modifier. This modifier marks a class or function as extensible. By default, classes and functions are
         * `final`, meaning they cannot be subclassed or overridden.
         *
         * @author Cedric Hammes
         * @since  02/12/2024
         */
        val OPEN: Modifier = Modifier(0b0000_0010_0000_0000U)
    }
}
