package uk.co.mruoc.wso2.plugin

import groovy.util.logging.Slf4j
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import uk.co.mruoc.wso2.*

@Slf4j
class SetApiStatusTask extends Wso2ApiPluginTask implements SetStatusParams {

    @Input
    String apiName = ''

    @Input
    String apiVersion = ''

    @Input
    String provider = ''

    @Input
    String apiStatus = ''

    @Input
    @Optional
    boolean publishToGateway = true

    @Input
    @Optional
    boolean requireResubscription = false

    SetApiStatusTask() {
        description 'sets the status of an API'
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
    ApiStatus getStatus() {
        return ApiStatus.parse(apiStatus)
    }

    @Override
    boolean isPublishToGateway() {
        return publishToGateway
    }

    @Override
    boolean isRequireResubscription() {
        return requireResubscription
    }

    @Override
    perform(ApiPublisherClient client) {
        log.info("attempting to update api status for " + apiName + " " + apiVersion + " to " + status)
        if (checkApiHasRequiredStatus(client)) {
            log.info(apiName + " " + apiVersion + " is already at status " + apiStatus + " no need to update")
            return
        }
        client.setStatus(this)
        if (!checkApiHasRequiredStatus(client)) {
            throw new Wso2ApiPluginException("api status failed to update")
        }
    }

    private Api getApi(ApiPublisherClient client) {
        Api api = client.getApi(this)
        log.info("got api " + apiName + " with status " + api.status)
        return api
    }

    private boolean checkApiHasRequiredStatus(ApiPublisherClient client) {
        Api api = getApi(client)
        return api.getStatus() == ApiStatus.parse(apiStatus)
    }

}

