package uk.co.mruoc.wso2.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import uk.co.mruoc.wso2.ApiPublisherClient
import uk.co.mruoc.wso2.Credentials
import uk.co.mruoc.wso2.DefaultApiPublisherClient

abstract class Wso2ApiPluginTask extends DefaultTask {

    private static final EXTENSION_NAME = "apiPublisher"

    def getExtension() {
        def extensions = project.extensions
        return extensions.findByName(EXTENSION_NAME) as Wso2ApiPublisherExtension
    }

    @TaskAction
    run() {
        def client = new DefaultApiPublisherClient(extension.hostUrl)
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
