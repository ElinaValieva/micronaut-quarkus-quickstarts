oc create -f openshift.yml | oc apply -f-
oc tag elvaliev/micronaut-quickstart micronaut-quickstart:latest
oc expose svc/micronaut-quickstart