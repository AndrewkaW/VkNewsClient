pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        //VKID
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-android/")
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/maven/")
    }
}
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        //VKID
        maven { setUrl("https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-android/") }
        maven { setUrl("https://artifactory-external.vkpartner.ru/artifactory/vk-id-captcha/android/") }
        maven { setUrl("https://artifactory-external.vkpartner.ru/artifactory/maven/") }
    }
}

rootProject.name = "VkNewsClient"
include(":app")
 