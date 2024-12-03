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
}

val javaTarget = libs.versions.jvm.target.get()

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
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(project(":furlang-parser-frontend"))
        }
    }
}
