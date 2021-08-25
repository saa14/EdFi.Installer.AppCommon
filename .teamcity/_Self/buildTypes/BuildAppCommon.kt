// SPDX-License-Identifier: Apache-2.0
// Licensed to the Ed-Fi Alliance under one or more agreements.
// The Ed-Fi Alliance licenses this file to you under the Apache License, Version 2.0.
// See the LICENSE and NOTICES files in the project root for more information.

package _self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object BuildAppCommon : BuildType ({
    name = "Build Ed-Fi Installer AppCommon"
    description = "Commonly used PowerShell scripts for supporting Ed-Fi application installations"

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = "**/EdFi.Installer.AppCommon*.nupkg"

    vcs {
        root(DslContext.settingsRoot)
    }

    params {
        param("github.organization", "Ed-Fi-Alliance-OSS")
        param("env.VSS_NUGET_EXTERNAL_FEED_ENDPOINTS", """{"endpointCredentials": [{"endpoint": "%azureArtifacts.feed.nuget%","username": "%azureArtifacts.edFiBuildAgent.userName%","password": "%azureArtifacts.edFiBuildAgent.accessToken%"}]}""")
        param("project.shouldPublishPreRelease", "true")
    }

    triggers {
        vcs {
            id ="vcsTrigger"
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 120
            branchFilter = """
                +:main
            """.trimIndent()
        }
    }

    steps {
        powerShell {
            name = "Build Pre-release and release, publish pre-release package"
            id = "PackageAndPublishInstallerLibrary_PackPreRelease"
            formatStderrAsError = true
            scriptMode = script {
                content = """
                    ${'$'}parameters = @{
                        SemanticVersion = "%appCommon.version%"
                        BuildCounter = "%build.counter%"
                        PreReleaseLabel = "%version.preReleaseLabel%"
                        Publish = [System.Convert]::ToBoolean("%project.shouldPublishPreRelease%")
                        NuGetFeed = "%azureArtifacts.feed.nuget%"
                        NuGetApiKey = "%azureArtifacts.edFiBuildAgent.accessToken%"
                    }
                    .\build-package.ps1 @parameters
                """.trimIndent()
            }
        }
    }

    features {
        swabra {
            forceCleanCheckout = true
        }
    }
})
