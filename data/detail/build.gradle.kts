import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.detail")
}

dependencies {
    implementation(projects.domain.detail)
}
