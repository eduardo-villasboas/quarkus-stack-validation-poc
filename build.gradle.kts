import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.allopen") version "1.7.22"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

sourceSets {
    create("componentTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }

}

val componentTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
    extendsFrom(configurations.testRuntimeOnly.get())
}

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.assertj:assertj-core:3.24.2")

}

group = "com.quarkus-app-server"
version = "1.0.0-SNAPSHOT"


allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

quarkus {
    sourceSets {
        setExtraNativeTest(sourceSets["componentTest"])
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

tasks.withType<io.quarkus.gradle.tasks.QuarkusDev> {
    jvmArgs = listOf("--enable-preview")
}

val componentTest = tasks.create("componentTest", Test::class) {

    description = "Runs the component tests"
    group = "verification"

    testClassesDirs = sourceSets["componentTest"].output.classesDirs
    classpath = sourceSets["componentTest"].runtimeClasspath

    useJUnitPlatform()

}

tasks.check { dependsOn(componentTest) }

tasks.named<Test>("componentTest") {
    shouldRunAfter("test")
}


tasks.named<Test>("testNative") {
    testClassesDirs = componentTest.testClassesDirs
    classpath = componentTest.classpath
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    tasks.withType<Test> {
        jvmArgs = listOf("--enable-preview")
        testLogging {
            events
            TestLogEvent.STARTED
            TestLogEvent.FAILED
            TestLogEvent.PASSED
            TestLogEvent.SKIPPED
            TestLogEvent.STANDARD_OUT

            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
            showCauses = true
            showStackTraces = true
            showStandardStreams = true

        }

        useJUnitPlatform()
    }

}

kotlin {
    kotlinDaemonJvmArgs = listOf("--enable-preview")
    jvmToolchain(17)
}
