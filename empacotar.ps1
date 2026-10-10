# Gera o executavel Windows do AutoImport MZ em dist\AutoImportMZ\AutoImportMZ.exe
# O executavel inclui o seu proprio Java, por isso corre em qualquer PC Windows
# sem ser preciso instalar Java nem Maven.
#
# Uso (na raiz do projeto):   .\empacotar.ps1
# Requer um JDK 17+ (usa o jpackage que vem com o JDK).

$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

# Encontrar o JDK: usa o JAVA_HOME se for valido, senao o java que estiver no PATH
if (-not $env:JAVA_HOME -or -not (Test-Path "$env:JAVA_HOME\bin\jpackage.exe"))
{
	$java = (Get-Command java -ErrorAction Stop).Source
	$env:JAVA_HOME = Split-Path (Split-Path $java)
	if (-not (Test-Path "$env:JAVA_HOME\bin\jpackage.exe"))
	{
		# O java do PATH pode ser um atalho do Oracle; procurar o JDK pelas propriedades
		# (via cmd, porque o java escreve isto no stderr e o PowerShell 5.1 trataria como erro)
		$props = cmd /c "java -XshowSettings:properties -version 2>&1"
		$env:JAVA_HOME = ($props | Select-String "java.home").ToString().Split("=")[1].Trim()
	}
}
$bin = "$env:JAVA_HOME\bin"
Write-Host "A usar o JDK em $env:JAVA_HOME"

Write-Host "1/3  A compilar e testar..."
& .\mvnw.cmd -q package
if ($LASTEXITCODE -ne 0) { throw "A compilacao falhou." }

Write-Host "2/3  A preparar ficheiros..."
$entrada = "target\jpackage-input"
Remove-Item -Recurse -Force $entrada, "dist\AutoImportMZ" -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force $entrada | Out-Null
Copy-Item "target\autoimport-mz-standalone.jar" $entrada

# Incluir so os modulos do Java que a app usa, para o executavel ficar mais pequeno
$modulos = (& "$bin\jdeps.exe" --print-module-deps --ignore-missing-deps --multi-release 17 "$entrada\autoimport-mz-standalone.jar").Trim()
Write-Host "     Modulos Java: $modulos"

Write-Host "3/3  A gerar o executavel..."
& "$bin\jpackage.exe" `
	--type app-image `
	--name AutoImportMZ `
	--app-version 1.0.0 `
	--vendor "Vicente Macuacua" `
	--description "Gestao de clientes e viaturas importadas" `
	--icon packaging\autoimport.ico `
	--input $entrada `
	--main-jar autoimport-mz-standalone.jar `
	--main-class autoimport.App `
	--add-modules $modulos `
	--java-options "--enable-native-access=ALL-UNNAMED" `
	--dest dist
if ($LASTEXITCODE -ne 0) { throw "O jpackage falhou." }

Write-Host ""
Write-Host "Pronto: dist\AutoImportMZ\AutoImportMZ.exe"
