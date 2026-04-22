import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

internal fun configureAndroid(extension: BaseExtension) {
    extension.apply {
        compileSdkVersion(36)
        defaultConfig {
            minSdk = 28
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
