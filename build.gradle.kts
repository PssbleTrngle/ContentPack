plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.vanilla") apply false
    id("com.possible-triangle.forge") apply false
    id("com.possible-triangle.fabric") apply false
}

subprojects {
    apply(plugin = "com.possible-triangle.core")

    repositories {
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
    }

    upload {
        maven {
            nexus()
        }
    }
}

tasks.withType<Jar> {
    exclude("datapacks")
}

enableSpotless()
enableSonarQube()
