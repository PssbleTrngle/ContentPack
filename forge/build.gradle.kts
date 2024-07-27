import net.minecraftforge.gradle.common.util.MinecraftExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension

val mod_id: String by extra
val mc_version: String by extra
val registrate_forge_version: String by extra
val create_forge_version: String by extra
val jei_version: String by extra
val flywheel_version: String by extra
val botania_forge_version: String by extra
val vslab_forge_version: String by extra

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

configure<MixinExtension> {
    config("${mod_id}.forge.mixins.json")
}

dependencies {
    modImplementation("com.tterrag.registrate:Registrate:${registrate_forge_version}")
    modImplementation("com.jozufozu.flywheel:flywheel-forge-${mc_version}:${flywheel_version}")
    modImplementation("com.simibubi.create:create-${mc_version}:${create_forge_version}:slim") {
        isTransitive = false
    }

    modCompileOnly("vazkii.botania:Botania:${botania_forge_version}")
    modCompileOnly("curse.maven:vertical-slabs-compat-724387:${vslab_forge_version}")

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
    }
}

uploadToCurseforge()
uploadToModrinth {
    syncBodyFromReadme()
}