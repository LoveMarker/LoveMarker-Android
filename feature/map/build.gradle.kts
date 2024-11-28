import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.map")
}

dependencies {
    implementation(projects.core.datastore)
    implementation(libs.bundles.google.map)
}
