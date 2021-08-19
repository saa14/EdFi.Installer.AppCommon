#requires -version 5
param (
    [string]
    [Parameter(Mandatory=$true)]
    $SemanticVersion,

    [string]
    [Parameter(Mandatory=$true)]
    $BuildCounter,

    [string]
    $PreReleaseLabel = "pre",

    [switch]
    $Publish,

    [string]
    $NuGetFeed,

    [string]
    $NuGetApiKey
)

$ErrorActionPreference = "Stop"

$verbose = $PSCmdlet.MyInvocation.BoundParameters["Verbose"]

Import-Module "$PSScriptRoot/Utility/create-package.psm1" -Force

$parameters = @{
    PackageDefinitionFile = Resolve-Path (Join-Path "$PSScriptRoot" "EdFi.Installer.AppCommon.nuspec")
    Version = $SemanticVersion
    Suffix = "$PreReleaseLabel$($BuildCounter.PadLeft(4,'0'))"
    OutputDirectory = Resolve-Path $PSScriptRoot
    Publish = $Publish
    Source = $NuGetFeed
    ApiKey = $NuGetApiKey
    ToolsPath = "$PSScriptRoot/../../tools"
}
Invoke-CreatePackage @parameters -Verbose:$verbose
