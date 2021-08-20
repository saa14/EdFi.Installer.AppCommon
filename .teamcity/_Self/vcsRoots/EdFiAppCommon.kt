// SPDX-License-Identifier: Apache-2.0
// Licensed to the Ed-Fi Alliance under one or more agreements.
// The Ed-Fi Alliance licenses this file to you under the Apache License, Version 2.0.
// See the LICENSE and NOTICES files in the project root for more information.

package _self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.RelativeId
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object EdFiAppCommon : GitVcsRoot({
    id = RelativeId("EdFi_AppCommon")
    name = "Ed-Fi-Installer-AppCommon"
    url = "https://github.com/saa14/EdFi.Installer.AppCommon.git"
    branch = "%git.branch.default%"
    branchSpec = "%git.branch.specification%"
    userNameStyle = GitVcsRoot.UserNameStyle.FULL
    checkoutSubmodules = GitVcsRoot.CheckoutSubmodules.IGNORE
    serverSideAutoCRLF = true
    useMirrors = false
    authMethod = anonymous()
})
