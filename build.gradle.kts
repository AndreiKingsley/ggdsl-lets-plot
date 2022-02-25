plugins {
    kotlin("jvm") version "1.6.0"
    `maven-publish`
}

val ggdslVersion = "0.1.2-dev-1.8-feature-1.1"
val ggdslLetsPlotVersion = "0.1.2-dev-1.8-facet-fix-0.2"

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
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.1.0")
    implementation("com.github.AndreiKingsley:ggdsl:$ggdslVersion")


    // https://jmaven.com/maven/org.jetbrains.lets-plot/lets-plot-image-export
   // implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.2.1")
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