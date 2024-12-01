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
    ":feature:main",
    ":feature:login",
    ":feature:nickname",
    ":feature:matching",
    ":feature:map",
    ":feature:archive",
    ":feature:mypage",
    ":feature:upload",
    ":feature:search",
    ":feature:detail",
)

include(
    ":domain:auth",
    ":domain:oauth",
    ":domain:nickname",
    ":domain:matching",
    ":domain:mypage",
    ":domain:archive",
    ":domain:upload",
    ":domain:search",
    ":domain:detail",
)

include(
    ":data:auth",
    ":data:oauth",
    ":data:nickname",
    ":data:matching",
    ":data:mypage",
    ":data:archive",
    ":data:upload",
    ":data:search",
    ":data:detail",
)
