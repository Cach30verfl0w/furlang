import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

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
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.antlr)
    id("maven-publish")
}

val generateKotlinGrammarSourceTask = tasks.register<AntlrKotlinTask>("generateKotlinGrammarSource") {
    dependsOn("cleanGenerateKotlinGrammarSource")
    source = fileTree(layout.projectDirectory.asFile.resolve("src")) {
        include("*.g4")
    }
    packageName = "${project.group}.frontend"
    arguments = listOf("-visitor")
    outputDirectory = layout.buildDirectory.dir("generatedAntlr/${project.group.toString().replace('.', '/')}/frontend").get().asFile
}

tasks.withType<AbstractKotlinCompile<*>> {
    dependsOn(generateKotlinGrammarSourceTask)
}

tasks.withType<KotlinNativeCompile> {
    dependsOn(generateKotlinGrammarSourceTask)
}

tasks.withType<Jar> {
    dependsOn(generateKotlinGrammarSourceTask)
}

kotlin {
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()
    mingwX64()
    
    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generatedAntlr"))
            dependencies {
                api(libs.antlr.runtime)
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/cach30verfl0w/furlang")
            credentials {
                username = project.findProperty("github.repository.user")?.toString()?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("github.repository.key")?.toString()?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications.configureEach {
        if (this !is MavenPublication) return@configureEach
        pom {
            name = project.name
            description = "This library provides the ANTLR4 lexer-parser frontend for Furlang"
            url = "https://github.com/cach30verfl0w/furlang"
            licenses {
                license {
                    name = "Apache License, version 2.0"
                    url = "https://www.apache.org/licenses/LICENSE-2.0"
                }
            }
            developers {
                developer {
                    id = "cach30verfl0w"
                    name = "Cedric Hammes"
                    email = "cach30verfl0w@gmail.com"
                    roles = listOf("Lead Developer")
                    timezone = "Europe/Berlin"
                }
            }
            scm {
                url.set(pom.url)
            }
        }
    }
}
