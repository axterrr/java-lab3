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
