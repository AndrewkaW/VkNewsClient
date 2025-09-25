// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true //VKID
}

vkidManifestPlaceholders {

    fun error() = logger.error(
        "Warning! Build will not work!\nCreate the 'secrets.properties' file in the 'sample/app' folder and add your 'VKIDClientID' and 'VKIDClientSecret' to it." +
                "\nFor more information, refer to the 'README.md' file."
    )

    val properties = Properties()
    properties.load(file("app/secrets.properties").inputStream())
    val clientId = properties["VKIDClientID"] ?: error()
    val clientSecret = properties["VKIDClientSecret"] ?: error()
    init(
        clientId = clientId.toString(),
        clientSecret = clientSecret.toString(),
    )

    vkidRedirectHost = "vk.ru"
    vkidRedirectScheme = "vk${clientId}"
    vkidClientId = clientId.toString()
    vkidClientSecret = clientSecret.toString()
}