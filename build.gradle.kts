plugins {
    kotlin("jvm") version "1.7.21"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>(){
        kotlinOptions.jvmTarget = "17"
    }

}
