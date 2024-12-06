import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.map")
}

dependencies {
    implementation(libs.compose.maps)
    implementation(libs.google.maps)
    implementation(libs.google.places)
    implementation(libs.google.location)

    implementation(projects.domain.mypage)
    implementation(projects.core.datastore)
}
