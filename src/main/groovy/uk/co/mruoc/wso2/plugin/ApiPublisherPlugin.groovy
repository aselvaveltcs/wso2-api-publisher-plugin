package uk.co.mruoc.wso2.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApiPublisherPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('apiPublisher', Wso2ApiPublisherExtension)
    }

}