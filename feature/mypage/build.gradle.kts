import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.feature)
}

android {
    setNamespace("feature.mypage")
}

dependencies {
//    implementation(projects.domain.mypage)
}
