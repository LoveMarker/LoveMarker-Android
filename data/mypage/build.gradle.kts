import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.mypage")
}

dependencies {
    implementation(projects.domain.mypage)
}
