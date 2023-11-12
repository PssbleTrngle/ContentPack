val mc_version: String by extra
val moonlight_forge_version: String by extra
val registrate_forge_version: String by extra
val create_forge_version: String by extra
val jei_version: String by extra

forge {
    enableMixins()

    dependOn(project(":common"))

    includesMod("com.tterrag.registrate:Registrate:${registrate_forge_version}")
}

dependencies {
    modImplementation("maven.modrinth:moonlight:${moonlight_forge_version}")
    modImplementation("com.simibubi.create:create-${mc_version}:${create_forge_version}:slim") {
        isTransitive = false
    }

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
    }
}

uploadToCurseforge()
uploadToModrinth {
    syncBodyFromReadme()
}