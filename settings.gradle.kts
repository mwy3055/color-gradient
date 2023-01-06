pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle-build-scripts/libs.versions.toml"))
        }
    }
}
rootProject.name = "Color Gradient"
include(":app")
