plugins {
	kotlin("jvm") version "1.9.21"
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.mrshawn"
version = "1.0.0"
extra["apiVersion"] = "1.20"

repositories {
	mavenCentral()
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
	implementation("dev.mrshawn:Mlib:0.0.202")
}

tasks.withType<JavaCompile> {
	sourceCompatibility = "1.8"
	targetCompatibility = "1.8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}

tasks.processResources {
	filesMatching("plugin.yml") {
		expand(mapOf("version" to version, "apiVersion" to project.extra["apiVersion"], "name" to project.name))
	}
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
	archiveBaseName.set("EliteFactions")
	archiveVersion.set("${project.version}")
	archiveClassifier.set("")
	relocate("dev.mrshawn.mlib", "${project.group}.shade.mlib")
	relocate("kotlin", "${project.group}.shade.kotlin")
	minimize()
}

artifacts {
	archives(tasks.named("shadowJar"))
}