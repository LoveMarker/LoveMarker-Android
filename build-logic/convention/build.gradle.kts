plugins {
    `kotlin-dsl`
}

group = "com.capstone.lovemarker.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.agp)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "lovemarker.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "lovemarker.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidCompose") {
            id = "lovemarker.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("kotlinLibrary") {
            id = "lovemarker.kotlin.library"
            implementationClass = "KotlinLibraryPlugin"
        }
        register("loveMarkerFeature") {
            id = "lovemarker.feature"
            implementationClass = "LoveMarkerFeaturePlugin"
        }
        register("loveMarkerData") {
            id = "lovemarker.data"
            implementationClass = "LoveMarkerDataPlugin"
        }
    }
}
