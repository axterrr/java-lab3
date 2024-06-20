import java.net.HttpURLConnection
import java.net.URI

plugins {
    id("java")
}

group = "hibskyi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("checkKeyApiExistence") {
    group = "custom"
    doLast {
        val filePath = "lib/src/main/resources/exchangeRateApiKey.txt"
        val file = file(filePath)
        if (!file.exists())
            throw GradleException("$filePath is not found")
    }
}

tasks.register("checkExternalResourceAvailability") {
    group = "custom"
    doLast {
        try {
            val uri = URI("https://v6.exchangerate-api.com")
            val url = uri.toURL()
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK)
                throw GradleException("Resources are not available")
        } catch (e: Exception) {
            throw GradleException("External resources are not available")
        }
    }
}

tasks.named("build") {
    dependsOn("checkExternalResourceAvailability", "checkKeyApiExistence")
}
