import com.capstone.lovemarker.convention.setNamespace
import java.util.Properties

plugins {
    alias(libs.plugins.lovemarker.data)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    setNamespace("data.oauth")

    defaultConfig {
        val webClientId = properties["web.client.id"] as? String ?: ""
        buildConfigField("String", "WEB_CLIENT_ID", webClientId)
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain.oauth)
    implementation(libs.bundles.google.login)
}
