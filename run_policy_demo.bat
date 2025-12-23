@echo off
rem Demo script: build project, run App with several configs and collect outputs
set OUT=policy_demo.txt
echo Demo generated on %date% %time% > %OUT%

echo === Build (skip tests) === >> %OUT%
mvn -DskipTests package >> %OUT% 2>&1

echo. >> %OUT%
echo === Default policy (no args) === >> %OUT%
java -cp target\classes com.example.security.App >> %OUT% 2>&1

echo. >> %OUT%
echo === Preset: weak === >> %OUT%
java -cp target\classes com.example.security.App weak >> %OUT% 2>&1

echo. >> %OUT%
echo === Preset: medium === >> %OUT%
java -cp target\classes com.example.security.App medium >> %OUT% 2>&1

echo. >> %OUT%
echo === Preset: strong === >> %OUT%
java -cp target\classes com.example.security.App strong >> %OUT% 2>&1

echo. >> %OUT%
echo === Preset: very_strong === >> %OUT%
java -cp target\classes com.example.security.App very_strong >> %OUT% 2>&1

echo. >> %OUT%
echo === Custom: minLength=10 requireSymbols=false === >> %OUT%
java -cp target\classes com.example.security.App "minLength=10" "requireSymbols=false" >> %OUT% 2>&1

echo. >> %OUT%
echo Demo complete. >> %OUT%

echo policy_demo.txt created.

type %OUT%
pause

