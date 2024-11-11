import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.nickname")
}

dependencies {
    implementation(projects.domain.nickname)
}
