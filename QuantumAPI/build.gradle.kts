repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven {
        url = uri("https://ci.ender.zone/plugin/repository/everything/")
    }

    maven {
        url = uri("https://repo.phoenix616.dev/")
    }

}

dependencies {
    implementation("org.spongepowered:configurate-hocon:4.0.0")

    implementation("net.kyori:adventure-api:4.3.0")
    implementation("net.kyori:adventure-platform-bukkit:4.0.0-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")

    api("cloud.commandframework:cloud-paper:1.4.0")

    // integrations
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4")
    implementation("com.massivecraft:Factions:1.6.9.5-U0.5.20")

    implementation("org.kitteh:paste-gg-api") {
        version {
            strictly("0.9.0")
        }
    }

}