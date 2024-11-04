import com.capstone.lovemarker.convention.setNamespace
import java.util.Properties

plugins {
    alias(libs.plugins.lovemarker.feature)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    setNamespace("feature.login")

    defaultConfig {
        val webClientId = properties["web.client.id"] as? String ?: ""
        buildConfigField("String", "WEB_CLIENT_ID", webClientId)
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain.auth)

    implementation(libs.credentials)
    implementation(libs.credentials.play.services)
    implementation(libs.googleid)
}
