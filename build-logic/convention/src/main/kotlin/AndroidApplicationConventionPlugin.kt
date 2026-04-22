import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            val base = extensions.getByType<BaseExtension>()
            configureAndroid(base)
            extensions.getByType<ApplicationExtension>().defaultConfig.targetSdk = 36
        }
    }
}
