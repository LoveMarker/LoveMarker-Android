import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.archive")
}

dependencies {
    implementation(projects.domain.archive)
}
