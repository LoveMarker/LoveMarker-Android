import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.map")
}

dependencies {
    // modules
    implementation(projects.domain.mypage)
    implementation(projects.core.datastore)

    // libraries
    implementation(libs.bundles.google.maps)
}
