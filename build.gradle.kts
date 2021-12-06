plugins {
    kotlin("jvm") version "1.6.0"
    `maven-publish`
}

group = "com.andreikingsley"
version = "0.1.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.1.0")
    implementation("com.github.AndreiKingsley:ggdsl:0.1.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.andreikingsley"
            artifactId = "ggdsl-lets-plot"
            version = "0.1.1"

            from(components["java"])
        }
    }
}