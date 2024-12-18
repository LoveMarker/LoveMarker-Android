import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.main")
}

dependencies {
    // feature
    implementation(projects.feature.login)
    implementation(projects.feature.nickname)
    implementation(projects.feature.matching)
    implementation(projects.feature.map)
    implementation(projects.feature.archive)
    implementation(projects.feature.mypage)
    implementation(projects.feature.myfeed)
    implementation(projects.feature.detail)
    implementation(projects.feature.upload)
    implementation(projects.feature.search)

    // domain
    implementation(projects.domain.auth)
}
