import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.archive")
}

dependencies {
//    implementation(projects.domain.archive)
}
