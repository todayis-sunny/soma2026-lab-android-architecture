plugins {
    `kotlin-dsl`
}

group = "com.soma2026.lab.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "soma2026.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "soma2026.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "soma2026.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("kotlinJvm") {
            id = "soma2026.kotlin.jvm"
            implementationClass = "KotlinJvmConventionPlugin"
        }
    }
}
