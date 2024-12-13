import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.data)
}

android {
    setNamespace("data.myfeed")
}

dependencies {
    implementation(projects.domain.myfeed)
    implementation(libs.paging)
}
