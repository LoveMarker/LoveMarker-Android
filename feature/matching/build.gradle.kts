import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.matching")
}

dependencies {
    implementation(projects.domain.matching)
    implementation(projects.domain.mypage)
    implementation(projects.core.datastore)
}
