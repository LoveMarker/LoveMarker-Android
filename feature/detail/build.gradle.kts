import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.detail")
}

dependencies {
    implementation(projects.domain.detail)
}
