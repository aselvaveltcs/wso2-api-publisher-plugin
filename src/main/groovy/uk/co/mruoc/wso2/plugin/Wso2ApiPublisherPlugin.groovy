package uk.co.mruoc.wso2.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class Wso2ApiPublisherPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('apiPublisher', Wso2ApiPublisherExtension)
        project.task('updateApi', type: UpdateApiTask)
        project.task('setApiStatus', type: SetApiStatusTask)
        project.task('removeApi', type: RemoveApiTask)
    }

}