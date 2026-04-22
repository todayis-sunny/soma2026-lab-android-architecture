import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // AGP 9.x + built-in Kotlin: hilt plugin + annotationProcessor
            pluginManager.apply("com.google.dagger.hilt.android")
            dependencies {
                add("implementation", "com.google.dagger:hilt-android:2.59.2")
                add("annotationProcessor", "com.google.dagger:hilt-android-compiler:2.59.2")
            }
        }
    }
}
