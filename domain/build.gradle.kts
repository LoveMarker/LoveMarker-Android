plugins {
    lovemarker("jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.javax.inject)
}