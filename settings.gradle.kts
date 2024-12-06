pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":furlang-compiler-frontend",
    ":furlang-parser-frontend",
    ":furlang-plugin-intellij"
)
rootProject.name = "furlang"
