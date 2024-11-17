import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.matching")
}

dependencies {
    implementation(projects.domain.matching)
}
