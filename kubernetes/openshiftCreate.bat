@echo off

set application=%1
set image=%2
set templatePath=%3

chcp 65001

echo Starting creating %application% from docker registry %image% by template from %templatePath%
oc create -f %templatePath% | oc apply -f-
oc tag %image% %application%":latest
oc expose svc/%image%