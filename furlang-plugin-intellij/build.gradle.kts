import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
 */

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.intellij)
    id("java")
    id("antlr")
}

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.7.2")
    implementation(libs.antlr.intellij)
}

intellij {
    pluginName = findProperty("plugin.intellij.name").toString()
    version = findProperty("plugin.intellij.version").toString()
    findProperty("plugin.intellij.type")?.let { type = it.toString() }
}

tasks {
    val jvmTarget = libs.versions.jvm.target.get()
    withType<JavaCompile> {
        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }
    withType<KotlinCompile> {
        dependsOn(named("generateGrammarSource"))
        compilerOptions.jvmTarget.set(JvmTarget.valueOf("JVM_$jvmTarget"))
    }
    withType<PatchPluginXmlTask> {
        version.set(project.version.toString())
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }
    withType<AntlrTask> {
        val buildFolder = layout.buildDirectory.asFile.get().resolve("generated-src").resolve("antlr").resolve("main")
        source(requireNotNull(parent).layout.projectDirectory.asFile.resolve("furlang-parser-frontend/src").absolutePath)
        arguments.addAll(arrayOf("-package", "de.cacheoverflow.furlang.frontend", "-Xexact-output-dir"))
        outputDirectory = buildFolder.resolve("de/cacheoverflow/furlang/frontend")
        doFirst {
            layout.projectDirectory.asFile.resolve("src/main/antlr").mkdirs()
        }
    }
    withType<ProcessResources> {
        filesMatching("**/plugin.xml") {
            expand(
                "pluginId" to findProperty("project.group"),
                "pluginName" to findProperty("plugin.intellij.name"),
                "vendorEmail" to findProperty("plugin.intellij.vendor.mail"),
                "vendorUrl" to findProperty("plugin.intellij.vendor.url"),
                "vendorName" to findProperty("plugin.intellij.vendor.name")
            )
        }
    }
}
