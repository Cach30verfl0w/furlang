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

import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.konan.target.KonanTarget
import org.gradle.internal.os.OperatingSystem

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
    alias(libs.plugins.kotest)
    alias(libs.plugins.download)
    id("maven-publish")
    idea
}

val javaTarget = libs.versions.jvm.target.get()

val releaseVersion = libs.versions.llvm.release.get()
val llvmSourceTree = layout.buildDirectory.dir("llvm-$releaseVersion").get()
val llvmArchive = llvmSourceTree.file("llvm.tar.xz").asFile

val downloadLLVMTask = tasks.create<Download>("downloadLLVM") {
    val system = OperatingSystem.current().name.lowercase().replace("linux", "ubuntu-22.04")
    val arch = System.getProperty("os.arch").lowercase().replace("amd64", "x86_64")
    val prebuiltVersion = libs.versions.llvm.prebuilt.get()
    
    src("https://github.com/awakecoding/llvm-prebuilt/releases/download/$prebuiltVersion/clang+llvm-$releaseVersion-$arch-$system.tar.xz")
    dest(llvmArchive)
    overwrite(false)
}

val unpackLLVMTask = tasks.create<Exec>("unpackLLVM") {
    dependsOn(downloadLLVMTask)
    onlyIf { !llvmSourceTree.asFile.resolve("include").exists() }
    commandLine("tar", "xf", llvmArchive.absolutePath, "-C", llvmSourceTree.asFile.absolutePath, "--strip-components=1")
}

kotlin {
    jvmToolchain(javaTarget.toInt())
    
    val systemName = OperatingSystem.current().name.lowercase()
    val systemArch = System.getProperty("os.arch").replace("amd64", "x64")
    val konanTarget = KonanTarget.predefinedTargets["${systemName}_${systemArch}"]
    when (konanTarget) {
        KonanTarget.LINUX_X64 -> linuxX64()
        KonanTarget.LINUX_ARM64 -> linuxArm64()
        KonanTarget.MACOS_X64 -> macosX64()
        KonanTarget.MACOS_ARM64 -> macosArm64()
        null -> throw GradleException("Target '$systemName' (with arch '$systemArch') is currently not supported")
        else -> throw GradleException("Target '${konanTarget.visibleName}' is currently not supported")
    }.apply {
        compilations.getByName("main").cinterops.create("llvm") {
            defFile(project.layout.projectDirectory.asFile.resolve("src/nativeMain/cinterop/llvm.def").absolutePath)
            includeDirs(llvmSourceTree.dir("include"))
            tasks.named(interopProcessingTaskName) {
                dependsOn(unpackLLVMTask)
            }
        }
        binaries.executable {
            entryPoint = "de.cacheoverflow.furlang.compiler.executable.main"
            linkerOpts("-L${llvmSourceTree.asFile.resolve("lib").absolutePath}", "-lc")
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(project(":furlang-parser-frontend"))
            implementation(libs.clikt)
            api(libs.kotlin.logging)
            api(libs.mordant)
            api(libs.kotlinx.io)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.bundles.kotest)
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
            description = "This library contains the Frontend of the Furlang compiler"
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
