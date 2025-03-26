plugins {
    idea
    id("com.possible-triangle.gradle") version ("0.2.5")
}

subprojects {
    repositories {
        modrinthMaven()
        curseMaven()

        maven {
            url = uri("https://mvn.devos.one/snapshots/")
            content {
                includeGroup("com.tterrag.registrate_fabric")
                includeGroup("io.github.fabricators_of_create.Porting-Lib")
            }
        }

        maven {
            url = uri("https://maven.tterrag.com/")
            content {
                includeGroup("com.tterrag.registrate")
            }
        }

        maven {
            url = uri("https://maven.createmod.net")
            content {
                includeGroup("com.simibubi.create")
                includeGroup("net.createmod.ponder")
                includeGroup("dev.engine-room.flywheel")
            }
        }

        maven {
            url = uri("https://maven.blamejared.com/")
            content {
                includeGroup("mezz.jei")
                includeGroup("vazkii.botania")
            }
        }

        maven {
            url = uri("https://jitpack.io")
            content {
                includeGroup("com.github.LlamaLad7")
                includeGroup("com.github.llamalad7.mixinextras")
            }
        }
    }

    enablePublishing {
        githubPackages()
    }
}

