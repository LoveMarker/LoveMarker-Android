import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.auth")
}

dependencies {
    implementation(projects.domain.auth)
}
