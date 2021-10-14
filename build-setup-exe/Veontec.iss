; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

; Variables para el proceso de instalacion
#define MyAppName "Veontec"
#define MyAppVersion "v0.9.7"
#define MyAppProduccion "Alpha"
#define MyAppNameFull MyAppName + " " + MyAppVersion + MyAppProduccion
#define MyAppPublisher "ProgrammerAuditore <victorvj098@gmail.com>"
#define MyAppPublisherURL "https://programmerauditore.gitlab.io/veontec"
#define AppCopyright "Copyright Equipo TEC (c) 2021"

; Variables para compilar y crear la instalacion (Modificar)
#define MyAppRunName MyAppName + ".jar" ; Sin JRE
#define MyAppExeName MyAppName + ".jar" ; Sin JRE
#define MyAppDir "Veontec" ; Sin JRE
#define MyAppIconoDefault "icons/veontec.ico"
#define MyAppIconoDesinstall "icons/uninstall.ico"

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{D7C7ADBF-5D86-431B-BAC7-F34C7D626E41}
AppName={#MyAppNameFull}
AppVersion={#MyAppVersion}{#MyAppProduccion}
AppVerName={#MyAppNameFull}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppPublisherURL}
AppCopyright={#AppCopyright}
DefaultDirName={autopf}\{#MyAppName}
DefaultGroupName={#MyAppName}
CloseApplications=yes
RestartApplications=yes
UsePreviousAppDir=yes
DirExistsWarning=yes
AllowNoIcons=no
;; (Modificar)
;;LicenseFile={#MyAppDir}\gpl-3.0.txt
; Uncomment the following line to run in non administrative install mode (install for current user only.)
PrivilegesRequired=admin
;PrivilegesRequiredOverridesAllowed=dialog
OutputDir=Setup Veontec
OutputBaseFilename=Setup - {#MyAppNameFull}
;SetupIconFile=
Compression=lzma
SolidCompression=yes
WizardStyle=modern

; ## Establecer los datos de desinstalacio  
; Establecer un icono para desinstalar
UninstallDisplayIcon={app}\{#MyAppIconoDesinstall}
; Establecer el nombre del desinstalar
UninstallDisplayName={#MyAppNameFull}

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
; Se crea un icono en el escritorio
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; Seleccionar el archivo de arranque (Programa) (Modificar)
Source: "{#MyAppDir}\{#MyAppRunName}"; DestDir: "{app}"; BeforeInstall: InstalandoEjecutable; Flags: ignoreversion replacesameversion restartreplace
; Seleccionar el ejecutable (Programa) (Modificar)
Source: "{#MyAppDir}\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion replacesameversion restartreplace 
Source: "{#MyAppDir}\*"; DestDir: "{app}"; Flags: ignoreversion replacesameversion restartreplace recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
; ### Se crean iconos en la barra de inicio de windows o busqueda de archivos
;; Seleccionar el archivo de arranque (Programa) (Modificar)
; Se instala el gropus de Inicio
Name: "{group}\{#MyAppNameFull}"; Filename: "{app}\{#MyAppRunName}"; WorkingDir: {app}; IconFilename: "{app}\{#MyAppIconoDefault}"
; El enlace en el escritorio
Name: "{autodesktop}\{#MyAppNameFull}"; Filename: "{app}\{#MyAppRunName}"; IconFilename: "{app}\{#MyAppIconoDefault}"; Tasks: desktopicon
;;
; Se creo el desinstalador 
Name: "{group}\{cm:UninstallProgram,{#MyAppNameFull}}"; Filename: "{uninstallexe}"; WorkingDir: {app}; IconFilename: "{app}\{#MyAppIconoDesinstall}"

[Registry]
; Create "Software\ProgrammerAuditore\Veontec" keys under CURRENT_USER or
; LOCAL_MACHINE depending on administrative or non administrative install
; mode. The flags tell it to always delete the "Veontec" key upon
; uninstall, and delete the "ProgrammerAuditore" key if there is nothing left in it.
Root: HKA; Subkey: "Software\ProgrammerAuditore"; Flags: uninsdeletekeyifempty
Root: HKA; Subkey: "Software\ProgrammerAuditore\Veontec"; Flags: uninsdeletekey
Root: HKA; Subkey: "Software\ProgrammerAuditore\Veontec\Settings"; ValueType: string; ValueName: "Language"; ValueData: "{language}"
; Associate .myp files with Veontec (requires ChangesAssociations=yes)
;Root: HKA; Subkey: "Software\Classes\.myp\OpenWithProgids"; ValueType: string; ValueName: "MyProgramFile.myp"; ValueData: ""; Flags: uninsdeletevalue
;Root: HKA; Subkey: "Software\Classes\MyProgramFile.myp"; ValueType: string; ValueName: ""; ValueData: "Veontec File"; Flags: uninsdeletekey
;Root: HKA; Subkey: "Software\Classes\MyProgramFile.myp\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\App.jar,0"
;Root: HKA; Subkey: "Software\Classes\MyProgramFile.myp\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\App.jar"" ""%1"""
;Root: HKA; Subkey: "Software\Classes\Applications\App.jar\SupportedTypes"; ValueType: string; ValueName: ".myp"; ValueData: ""
; HKA (and HKCU) should only be used for settings which are compatible with
; roaming profiles so settings like paths should be written to HKLM, which
; is only possible in administrative install mode.
Root: HKLM; Subkey: "Software\ProgrammerAuditore"; Flags: uninsdeletekeyifempty; Check: IsAdminInstallMode
Root: HKLM; Subkey: "Software\ProgrammerAuditore\Veontec"; Flags: uninsdeletekey; Check: IsAdminInstallMode
Root: HKLM; Subkey: "Software\ProgrammerAuditore\Veontec\Settings"; ValueType: string; ValueName: "InstallPath"; ValueData: "{app}"; Check: IsAdminInstallMode
; User specific settings should always be written to HKCU, which should only
; be done in non administrative install mode.
Root: HKCU; Subkey: "Software\ProgrammerAuditore\Veontec\Settings"; ValueType: string; ValueName: "UserName"; ValueData: "{userinfoname}"; Check: not IsAdminInstallMode
Root: HKCU; Subkey: "Software\ProgrammerAuditore\Veontec\Settings"; ValueType: string; ValueName: "UserOrganization"; ValueData: "{userinfoorg}"; Check: not IsAdminInstallMode

[Code]
function ShouldSkipPage(PageID: Integer): Boolean;
begin
  // User specific pages should be skipped in administrative install mode
  Result := IsAdminInstallMode and (PageID = wpUserInfo);
end;

procedure InstalandoEjecutable();
begin
  if (FileExists(ExpandConstant('{app}\{#MyAppExeName}'))) then
    begin
      MsgBox('Se est� instalando {#MyAppNameFull} ...' #13#13 ''+
      + 'Es recomendable tener el software {#MyAppName} cerrado durante la instalaci�n.',
      mbInformation, MB_OK); 
    end
  else
    begin
      MsgBox('Se est� instalando {#MyAppNameFull} ...' #13#13 ''+
      + 'Asegures� de instalar y configurar Java JRE para iniciar a utilizar {#MyAppName}.',
      mbInformation, MB_OK);     
    end;
end;

[Run]
; Nota: Esto se ejecuta al momento de la instalación de nuestro programa

; Instalamos MySQL
; (Aquí tenemos que poner el nombre de nuestro motor de base de datos que se encuentra en nuestra carpeta complementos)
; Filename: {src}\complementos\wampserver.exe; Parameters: "/q:a /C:""install /q"""; WorkingDir: {src}\complementos;

Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall nowait skipifsilent

