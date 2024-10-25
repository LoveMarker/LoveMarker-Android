import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.android.library)
    alias(libs.plugins.lovemarker.android.compose)
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    // core
    implementation(projects.core.common)

    // other
    implementation(libs.appcompat)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.immutable)
}