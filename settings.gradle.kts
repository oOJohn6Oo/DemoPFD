dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SceneNavigation"
include(":app")
include(":base")
include(":feature_ngapi")
include(":feature_ktv")
