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
    ":furlang-model-frontend"
)
rootProject.name = "furlang"
