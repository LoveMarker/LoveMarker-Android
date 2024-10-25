import com.capstone.lovemarker.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class LoveMarkerDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("lovemarker.android.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                add("implementation", project(":core:network"))
                add("implementation", libs.findBundle("retrofit").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("kotlinx.immutable").get())
            }
        }
    }
}
