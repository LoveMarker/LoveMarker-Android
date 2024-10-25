import com.capstone.lovemarker.convention.configureCoroutineAndroid
import com.capstone.lovemarker.convention.configureHiltAndroid
import com.capstone.lovemarker.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            configureKotlinAndroid()
            configureCoroutineAndroid()
            configureHiltAndroid()
        }
    }
}
