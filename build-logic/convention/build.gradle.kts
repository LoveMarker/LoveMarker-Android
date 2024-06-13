plugins {
    `kotlin-dsl`
}

group = "com.capstone.lovemarker.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
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
            implementationClass = "com.capstone.lovemarker.plugin.AndroidApplicationPlugin"
        }
        create("android-library") {
            id = "com.capstone.lovemarker.library"
            implementationClass = "com.capstone.lovemarker.plugin.AndroidLibraryPlugin"
        }
        create("android-kotlin") {
            id = "com.capstone.lovemarker.kotlin"
            implementationClass = "com.capstone.lovemarker.plugin.AndroidKotlinPlugin"
        }
        create("android-hilt") {
            id = "com.capstone.lovemarker.hilt"
            implementationClass = "com.capstone.lovemarker.plugin.AndroidHiltPlugin"
        }
        create("android-compose") {
            id = "com.capstone.lovemarker.compose"
            implementationClass = "com.capstone.lovemarker.plugin.AndroidComposePlugin"
        }
        create("kotlin-serialization") {
            id = "com.capstone.lovemarker.serialization"
            implementationClass = "com.capstone.lovemarker.plugin.KotlinSerializationPlugin"
        }
        create("kotlin-jvm") {
            id = "com.capstone.lovemarker.jvm"
            implementationClass = "com.capstone.lovemarker.plugin.KotlinJvmPlugin"
        }
        create("retrofit") {
            id = "com.capstone.lovemarker.retrofit"
            implementationClass = "com.capstone.lovemarker.plugin.RetrofitPlugin"
        }
    }
}
