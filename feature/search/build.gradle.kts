import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.search")
}

dependencies {
    implementation(projects.domain.search)
    implementation(libs.bundles.google.maps)
}
