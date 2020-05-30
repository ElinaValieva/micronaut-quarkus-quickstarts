@echo off

set application=%1
set image=%2
set templatePath=%3
set port=%4

chcp 65001

echo Starting creating %application% from docker registry %image% by template from %templatePath%
kubectl create deployment %application% --image=%image%
kubectl create -f %templatePath% --record --save-config
echo Expose %application% on port=%port%
kubectl expose deployment %application% --type=LoadBalancer --port=%port%
