import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.search")
}

dependencies {
    implementation(projects.domain.search)
    implementation(libs.google.places)
}
