import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.map")
}

dependencies {
    implementation(projects.domain.map)
}
