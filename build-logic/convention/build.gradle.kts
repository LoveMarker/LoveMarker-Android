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
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = "com.capstone.lovemarker.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        create("android-library") {
            id = "com.capstone.lovemarker.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        create("android-kotlin") {
            id = "com.capstone.lovemarker.kotlin"
            implementationClass = "AndroidKotlinPlugin"
        }
        create("android-hilt") {
            id = "com.capstone.lovemarker.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        create("android-compose") {
            id = "com.capstone.lovemarker.compose"
            implementationClass = "AndroidComposePlugin"
        }
        create("kotlin-serialization") {
            id = "com.capstone.lovemarker.serialization"
            implementationClass = "KotlinSerializationPlugin"
        }
        create("retrofit") {
            id = "com.capstone.lovemarker.retrofit"
            implementationClass = "RetrofitPlugin"
        }
    }
}
