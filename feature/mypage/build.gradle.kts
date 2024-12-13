import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.mypage")
}

dependencies {
    implementation(projects.domain.mypage)
    implementation(projects.domain.oauth)
    implementation(projects.core.datastore)

    implementation(libs.process.phoenix)
}
