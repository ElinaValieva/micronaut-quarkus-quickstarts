package com.elvaliev.k8s_aws_plugin.parser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class OpenshiftTemplate {

    var kind: String? = null

    var objects: List<OpenshiftConfiguration>? = null

    val parameters: List<OpenshiftParameters>? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class OpenshiftParameters {

        var name: String? = null

        var value: String? = null
    }
}