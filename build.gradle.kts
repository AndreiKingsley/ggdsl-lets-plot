plugins {
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.kotlin.jupyter.api") version "0.11.0-89-1"
    `maven-publish`
}

val ggdslVersion = "0.6.5-3"
val ggdslLetsPlotVersion = "0.6.5-3"

group = "com.andreikingsley"
version = ggdslLetsPlotVersion

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.2.0")
    implementation("com.github.AndreiKingsley:ggdsl:$ggdslVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.3.0")
    testImplementation(kotlin("test"))
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
            version = ggdslLetsPlotVersion

            from(components["java"])
        }
    }
}