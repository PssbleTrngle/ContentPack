plugins {
    idea
    id("com.possible-triangle.gradle") version ("0.1.5")
}

subprojects {
    repositories {
        modrinthMaven()

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
                includeGroup("com.jozufozu.flywheel")
                includeGroup("com.simibubi.create")
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

