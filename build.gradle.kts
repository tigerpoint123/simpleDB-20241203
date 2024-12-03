plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    runtimeOnly("com.mysql:mysql-connector-j:9.1.0")

    testImplementation("org.assertj:assertj-core:3.26.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    implementation ("org.slf4j:slf4j-api:2.0.3") // SLF4J API
    implementation ("ch.qos.logback:logback-classic:1.2.11")// Logback 구현체
}

tasks.test {
    useJUnitPlatform()
}