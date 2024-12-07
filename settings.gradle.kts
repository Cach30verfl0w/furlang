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
    ":furlang-compiler-embeddable",
    ":furlang-parser-frontend",
    ":furlang-plugin-intellij"
)
rootProject.name = "furlang"
