import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.map")
}

dependencies {
    // module
    implementation(projects.domain.map)
    implementation(projects.domain.mypage)
    implementation(projects.core.datastore)

    // library
    implementation(libs.bundles.google.maps)
}
