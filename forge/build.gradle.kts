import net.minecraftforge.gradle.common.util.MinecraftExtension

val mc_version: String by extra
val moonlight_forge_version: String by extra
val registrate_forge_version: String by extra
val create_forge_version: String by extra
val jei_version: String by extra
val flywheel_version: String by extra
val botania_forge_version: String by extra

forge {
    enableMixins()

    dependOn(project(":common"))

    // includesMod("com.tterrag.registrate:Registrate:${registrate_forge_version}")
}

configure<MinecraftExtension> {
    runs {
        forEach {
            it.property("forge.logging.console.level", "debug")
        }
    }
}

dependencies {
    // modImplementation("maven.modrinth:moonlight:${moonlight_forge_version}")
    modImplementation("com.simibubi.create:create-${mc_version}:${create_forge_version}:slim") {
        isTransitive = false
    }

    modImplementation("vazkii.botania:Botania:${botania_forge_version}")

    if (!env.isCI) {
        modRuntimeOnly("com.tterrag.registrate:Registrate:${registrate_forge_version}")
        modRuntimeOnly("com.jozufozu.flywheel:flywheel-forge-${mc_version}:${flywheel_version}")
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
    }
}

uploadToCurseforge()
uploadToModrinth {
    syncBodyFromReadme()
}