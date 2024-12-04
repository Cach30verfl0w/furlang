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
    ":furlang-parser-frontend",
    ":furlang-model-frontend",
    ":furlang-plugin-intellij"
)
rootProject.name = "furlang"
