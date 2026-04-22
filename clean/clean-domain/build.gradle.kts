plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)  // JSR-330: @Inject, @Singleton (DI 프레임워크 무관)
    testImplementation(libs.junit)
}
