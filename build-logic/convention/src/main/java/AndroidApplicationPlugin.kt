import com.android.build.api.dsl.ApplicationExtension
import com.capstone.lovemarker.convention.configureHiltAndroid
import com.capstone.lovemarker.convention.configureKotlinAndroid
import com.capstone.lovemarker.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            configureKotlinAndroid()
            configureHiltAndroid()

            val properties = Properties().apply {
                load(rootProject.file("local.properties").inputStream())
            }

            extensions.configure<ApplicationExtension> {
                with(defaultConfig) {
                    targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                    versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                    versionName = libs.findVersion("versionName").get().requiredVersion

                    val authBaseUrl = properties["auth.base.url"] as? String ?: ""
                    buildConfigField("String", "AUTH_BASE_URL", authBaseUrl)
                }
            }
        }
    }
}
