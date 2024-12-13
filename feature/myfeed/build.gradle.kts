import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.myfeed")
}

dependencies {
    implementation(projects.domain.myfeed)
}
