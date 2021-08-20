// SPDX-License-Identifier: Apache-2.0
// Licensed to the Ed-Fi Alliance under one or more agreements.
// The Ed-Fi Alliance licenses this file to you under the Apache License, Version 2.0.
// See the LICENSE and NOTICES files in the project root for more information.

package _self

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object AppCommonProject : Project({
    description = "Build configurations managed through the Ed-Fi Installer AppCommon repository"

    params {
        param("build.feature.freeDiskSpace", "2gb")
        param("git.branch.default", "main")
        param("git.branch.specification", """
            +:refs/heads/(*)
            +:refs/(pull/*)/merge
        """.trimIndent())
        param("teamcity.ui.settings.readOnly","true")
        param("appCommon.version", "2.0.0")
        param("project.name", "EdFi.Installer.AppCommon")
        param("version.preReleaseLabel", "pre")
    }

    buildType(_self.buildTypes.BuildAppCommon)
    buildType(_self.buildTypes.ReleaseAppCommon)
})
