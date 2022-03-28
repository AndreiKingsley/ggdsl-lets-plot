plugins {
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.kotlin.jupyter.api") version "0.11.0-1"
    `maven-publish`
}

val ggdslVersion = "0.2.1-dev-0.2"
val ggdslLetsPlotVersion = "0.2.1-dev-0.3-fix-1"

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
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.1.1")
    implementation("com.github.AndreiKingsley:ggdsl:$ggdslVersion")


    //implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.2.1")
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