gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LoveMarker"
include(":app")

include(
    ":core:common",
    ":core:datastore",
    ":core:designsystem",
    ":core:network",
    ":core:navigation",
    ":core:model"
)

include(
    ":data:auth",
    ":data:oauth",
    ":data:nickname",
    ":data:matching",
    ":data:map",
    ":data:archive",
    ":data:mypage",
    ":data:detail",
    ":data:upload",
    ":data:search",
)

include(
    ":domain:auth",
    ":domain:oauth",
    ":domain:nickname",
    ":domain:matching",
    ":domain:map",
    ":domain:archive",
    ":domain:mypage",
    ":domain:detail",
    ":domain:upload",
    ":domain:search",
)

include(
    ":feature:main",
    ":feature:login",
    ":feature:nickname",
    ":feature:matching",
    ":feature:map",
    ":feature:archive",
    ":feature:mypage",
    ":feature:detail",
    ":feature:upload",
    ":feature:search",
)
