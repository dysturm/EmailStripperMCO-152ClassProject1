@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  touro startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and TOURO_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\touro-1.0-SNAPSHOT.jar;%APP_HOME%\lib\docx4j-ImportXHTML-6.0.1.jar;%APP_HOME%\lib\htmlcleaner-2.6.1.jar;%APP_HOME%\lib\jsoup-1.11.3.jar;%APP_HOME%\lib\poi-ooxml-3.15-beta1.jar;%APP_HOME%\lib\poi-3.15-beta1.jar;%APP_HOME%\lib\mail-1.4.1.jar;%APP_HOME%\lib\guice-4.2.1.jar;%APP_HOME%\lib\xhtmlrenderer-3.0.0.jar;%APP_HOME%\lib\docx4j-6.0.1.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.25.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.25.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\jdom2-2.0.5.jar;%APP_HOME%\lib\httpclient-4.5.6.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\poi-ooxml-schemas-3.15-beta1.jar;%APP_HOME%\lib\curvesapi-1.03.jar;%APP_HOME%\lib\activation-1.1.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\guava-25.1-android.jar;%APP_HOME%\lib\itext-2.1.7.jar;%APP_HOME%\lib\jaxb-svg11-1.0.2.jar;%APP_HOME%\lib\mbassador-1.2.4.2.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\commons-lang3-3.5.jar;%APP_HOME%\lib\commons-compress-1.12.jar;%APP_HOME%\lib\xmlgraphics-commons-2.3.jar;%APP_HOME%\lib\commons-io-2.5.jar;%APP_HOME%\lib\jackson-databind-2.7.9.4.jar;%APP_HOME%\lib\jackson-core-2.7.9.jar;%APP_HOME%\lib\avalon-framework-impl-4.3.1.jar;%APP_HOME%\lib\avalon-framework-api-4.3.1.jar;%APP_HOME%\lib\xalan-2.7.2.jar;%APP_HOME%\lib\wmf2svg-0.9.8.jar;%APP_HOME%\lib\antlr-runtime-3.5.2.jar;%APP_HOME%\lib\stringtemplate-3.2.1.jar;%APP_HOME%\lib\lorem-2.1.jar;%APP_HOME%\lib\xmlbeans-2.6.0.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-compat-qual-2.0.0.jar;%APP_HOME%\lib\error_prone_annotations-2.1.3.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.14.jar;%APP_HOME%\lib\httpcore-4.4.10.jar;%APP_HOME%\lib\jackson-annotations-2.7.0.jar;%APP_HOME%\lib\serializer-2.7.2.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\stax-api-1.0.1.jar

@rem Execute touro
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TOURO_OPTS%  -classpath "%CLASSPATH%" Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable TOURO_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%TOURO_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
