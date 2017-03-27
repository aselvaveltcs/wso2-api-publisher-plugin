package uk.co.mruoc.wso2.plugin

import groovy.util.logging.Slf4j
import org.gradle.api.tasks.Input
import uk.co.mruoc.wso2.SelectApiParams
import uk.co.mruoc.wso2.publisher.ApiPublisherClient

@Slf4j
class RemoveApiTask extends Wso2ApiPluginTask implements SelectApiParams {

    @Input
    String apiName = ''

    @Input
    String apiVersion = ''

    @Input
    String provider = ''

    RemoveApiTask() {
        description 'removes an API'
    }

    @Override
    String getApiName() {
        return apiName
    }

    @Override
    String getApiVersion() {
        return apiVersion
    }

    @Override
    String getProvider() {
        return provider
    }

    @Override
    perform(ApiPublisherClient client) {
        log.info("attempting to remove api " + apiName + " version " + apiVersion)
        client.removeApi(this)
        if (client.apiExists(apiName))
            throw new Wso2ApiPluginException("failed to remove api " + apiName + " " + apiVersion)
    }

}

