import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            val base = extensions.getByType<BaseExtension>()
            configureAndroid(base)
            base.defaultConfig.consumerProguardFiles("consumer-rules.pro")
        }
    }
}
