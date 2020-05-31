package com.elvaliev.k8s_plugin

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project

class KubernetesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def kubernetesExtension = project.extensions.create('kubernetes', KubernetesExtension)
        def openShiftExtension = project.extensions.create('openshift', KubernetesExtension)

        project.task('kubernetesCreate') {
            group = 'kubernetes'
            description = 'Kubernates Deployment'

            doFirst {
                println "Starting creating ${kubernetesExtension.application} from docker registry ${kubernetesExtension.image} by template from ${kubernetesExtension.path} "
            }
            doLast {
                project.exec {
                    commandLine osAdaptiveCommand("kubectl create deployment ${kubernetesExtension.application} --image=${kubernetesExtension.image}")
                }
                project.exec {
                    commandLine osAdaptiveCommand("kubectl create -f ${kubernetesExtension.path} --record --save-config")
                }
                project.exec {
                    println "Expose ${kubernetesExtension.application} on port=${kubernetesExtension.port}"
                    commandLine osAdaptiveCommand("kubectl expose deployment ${kubernetesExtension.application} --type=LoadBalancer --port=${kubernetesExtension.port}")
                }
            }
        }

        project.task('deployKubernetes') {
            group = 'kubernetes'
            description = 'Kubernates Redeployment'

            doFirst {
                println "Redeploy application ${kubernetesExtension.application} from docker image ${kubernetesExtension.image}"
            }
            doLast {
                project.exec {
                    commandLine osAdaptiveCommand("kubectl set image deployment ${kubernetesExtension.application}  ${kubernetesExtension.application}=${kubernetesExtension.port}")
                }
            }
        }

        project.task('openshiftCreate') {
            group = 'openshift'
            description = 'OpenShift Deployment'

            doFirst {
                println "Starting creating ${openShiftExtension.application} from docker registry ${openShiftExtension.image} by template from ${openShiftExtension.path}"
            }
            doLast {
                project.exec {
                    commandLine osAdaptiveCommand("oc create -f ${openShiftExtension.path} | oc apply -f-")
                }
                project.exec {
                    commandLine osAdaptiveCommand("oc tag ${openShiftExtension.image} ${openShiftExtension.application}:latest")
                }
                project.exec {
                    commandLine osAdaptiveCommand("oc expose svc/${openShiftExtension.application}")
                }
            }
        }

        project.task('openshiftDeploy') {
            group = 'openshift'
            description = 'OpenShift Redeployment'
            doFirst {
                println "Redeploy application ${openShiftExtension.application} from docker image ${openShiftExtension.image}"
            }
            doLast {
                project.exec {
                    commandLine osAdaptiveCommand("oc tag ${openShiftExtension.image} ${openShiftExtension.application}:latest")
                }
            }
        }
    }

    private static Iterable<String> osAdaptiveCommand(String command) {
        def newCommands = []
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            newCommands = ['cmd', '/c']
        }

        newCommands.add(command)
        return newCommands
    }
}
