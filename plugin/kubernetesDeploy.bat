@echo off
set application=%1
set image=%2

chcp 65001

echo Redeploy application %application% from docker image %image%
kubectl set image deployment/%application% %application%=%image%