import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}

android {
    namespace = "com.soma2026.lab.core.network"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val footballBaseUrl = localProperties["FOOTBALL_BASE_URL"]?.toString()
            ?: throw GradleException("local.properties에 FOOTBALL_BASE_URL을 추가해주세요")
        val footballApiKey = localProperties["FOOTBALL_API_KEY"]?.toString()
            ?: throw GradleException("local.properties에 FOOTBALL_API_KEY를 추가해주세요")

        buildConfigField("String", "FOOTBALL_BASE_URL", "\"$footballBaseUrl\"")
        buildConfigField("String", "FOOTBALL_API_KEY", "\"$footballApiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
    api(libs.gson)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
