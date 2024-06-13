import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.lovemarker(pluginName: String): PluginDependencySpec {
    return id("com.capstone.lovemarker.$pluginName")
}