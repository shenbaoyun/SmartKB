@REM ----------------------------------------------------------------------------
@REM Maven Wrapper 启动脚本（Windows 批处理版本）
@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Maven Wrapper startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and MAVEN_OPTS.
set MAVEN_OPTS=-Xmx512m -XX:MaxMetaspaceSize=256m
set MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"

set WRAPPER_JAR="%APP_HOME%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@rem Find java.exe
if exist %MAVEN_JAVA_EXE% goto findJavaFromJavaHome
set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% == 0 goto execute
echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.
goto fail

:findJavaFromJavaHome
set JAVA_EXE=%MAVEN_JAVA_EXE%

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\.mvn\wrapper\maven-wrapper.jar

@rem Execute Maven
"%JAVA_EXE%" %MAVEN_OPTS% -classpath "%CLASSPATH%" %WRAPPER_LAUNCHER% %MAVEN_CONFIG% %*
if %ERRORLEVEL% == 0 goto end

:fail
if not "%~1"=="execute" exit /b 1
exit /b 0

:end
exit /b %ERRORLEVEL%

