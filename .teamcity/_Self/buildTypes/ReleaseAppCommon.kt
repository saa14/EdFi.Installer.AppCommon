package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell

object ReleaseAppCommon : BuildType({
    name = "Release Ed-Fi Installer App Common"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(DslContext.settingsRoot)
    }

    params {
        param("env.VSS_NUGET_EXTERNAL_FEED_ENDPOINTS", """{"endpointCredentials": [{"endpoint": "%azureArtifacts.feed.nuget%","username": "%azureArtifacts.edFiBuildAgent.userName%","password": "%azureArtifacts.edFiBuildAgent.accessToken%"}]}""")
    }

    steps {
        powerShell {
            name = "Publish to Azure Artifacts"
            scriptMode = script {
                content = "nuget push -source %azureArtifacts.feed.nuget% -apikey az *.nupkg"
            }
        }
    }

    dependencies {
        artifacts(AbsoluteId("Experimental_SMinocha_AppCommon_BuildAppCommon")) {
            buildRule = lastSuccessful()
            artifactRules = """
                +:**/*.nupkg => .
                -:**/*-pre*.nupkg
            """.trimIndent()
        }
    }
})
