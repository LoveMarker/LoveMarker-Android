import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.upload")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.domain.upload)
}
