import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.android.library)
    alias(libs.plugins.lovemarker.android.compose)
}

android {
    setNamespace("core.common")
}

dependencies {

}