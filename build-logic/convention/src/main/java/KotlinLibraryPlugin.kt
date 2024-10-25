import com.capstone.lovemarker.convention.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }
            configureKotlin()
        }
    }
}
