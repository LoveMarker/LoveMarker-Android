import com.capstone.lovemarker.convention.setNamespace

plugins {
    alias(libs.plugins.lovemarker.android.library)
}

android {
    setNamespace("core.datastore")
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.datastore.pref)
}