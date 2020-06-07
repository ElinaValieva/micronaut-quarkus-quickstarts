package com.elvaliev.k8s_aws_plugin.parser

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class OpenshiftConfiguration {

    var kind: String? = null

    @JsonAlias("metadata")
    var metaData: OpenShiftMetadata? = null

    @JsonAlias("spec")
    val spec: OpenShiftSpec? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class OpenShiftMetadata {

        var name: String? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class OpenShiftSpec {

        var triggers: List<Trigger>? = null

        var ports: List<OpenshiftPort>? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        class Trigger {

            val type: String? = null

            val imageChangeParams: ImageChangeParam? = null

            @JsonIgnoreProperties(ignoreUnknown = true)
            class ImageChangeParam {

                val from: FromParam? = null

                @JsonIgnoreProperties(ignoreUnknown = true)
                class FromParam {

                    val name: String? = null
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        class OpenshiftPort {

            var name: String? = null

            var targetPort: String? = null
        }

    }
}