plugins {
    kotlin("jvm") version "1.6.0"
    `maven-publish`
}

val ggdslVersion = "0.1.2-dev-1.8"

group = "com.andreikingsley"
version = ggdslVersion

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.1.0")
    implementation("com.github.AndreiKingsley:ggdsl:$ggdslVersion")
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
            version = ggdslVersion

            from(components["java"])
        }
    }
}