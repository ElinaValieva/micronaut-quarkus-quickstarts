@echo off

set application=%1
set image=%2

chcp 65001

echo Redeploy application %application% from docker image %image%
oc tag %image% %application%:latest