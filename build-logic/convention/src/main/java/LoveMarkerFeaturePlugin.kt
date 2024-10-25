import com.capstone.lovemarker.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class LoveMarkerFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("lovemarker.android.library")
                apply("lovemarker.android.compose")
            }

            dependencies {
                // module
                add("implementation", project(":core:model"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:navigation"))

                // compose
                add("implementation", libs.findLibrary("compose-navigation").get())
                add("implementation", libs.findLibrary("compose-hilt-navigation").get())
                add("implementation", libs.findLibrary("compose-lifecycle").get())
                add("implementation", libs.findLibrary("compose-viewmodel").get())
                add("implementation", libs.findLibrary("kotlinx-immutable").get())

                // timber
                add("implementation", libs.findLibrary("timber").get())
            }
        }
    }
}
