package uk.co.mruoc.wso2.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import uk.co.mruoc.wso2.Credentials
import uk.co.mruoc.wso2.publisher.ApiPublisherClient

abstract class Wso2ApiPluginTask extends DefaultTask {

    private static final EXTENSION_NAME = "apiPublisher"

    def getExtension() {
        def extensions = project.extensions
        return extensions.findByName(EXTENSION_NAME) as Wso2ApiPublisherExtension
    }

    @TaskAction
    run() {
        def client = extension.getPublisherClient()
        Credentials credentials = extension.getCredentials()
        client.login(credentials)
        try {
            perform(client)
        } finally {
            client.logout()
        }
    }

    abstract perform(ApiPublisherClient client)

}
