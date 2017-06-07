package uk.co.mruoc.wso2.plugin

import groovy.util.logging.Slf4j
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.joda.time.DateTime
import uk.co.mruoc.wso2.ApiTierAvailability
import uk.co.mruoc.wso2.CommaSeparatedStringConverter
import uk.co.mruoc.wso2.publisher.ApiEndpointType
import uk.co.mruoc.wso2.publisher.ApiPublisherClient
import uk.co.mruoc.wso2.publisher.ApiSubscriptions
import uk.co.mruoc.wso2.publisher.ApiVisibility
import uk.co.mruoc.wso2.publisher.addapi.AddApiParams
import uk.co.mruoc.wso2.publisher.getapi.Api

import static org.joda.time.DateTimeZone.UTC

@Slf4j
class UpdateApiTask extends Wso2ApiPluginTask implements AddApiParams {

    @Input
    String apiName = ''

    @Input
    String apiDescription = ''

    @Input
    String apiVersion = ''

    @Input
    String tags = ''

    @Input
    String context = ''

    @Input
    String provider = ''

    @Input
    String endpointConfig = ''

    @Input
    String swagger = ''

    @Input
    @Optional
    String tiers = 'UNLIMITED'

    @Input
    @Optional
    String thumbnailImageUrl = ''

    @Input
    @Optional
    boolean defaultVersion = false

    @Input
    @Optional
    String visibility = 'PUBLIC'

    @Input
    @Optional
    String roles = ''

    @Input
    @Optional
    String endpointType = 'UNSECURED'

    @Input
    @Optional
    String endpointUsername = ''

    @Input
    @Optional
    String endpointPassword = ''

    @Input
    @Optional
    boolean responseCacheEnabled = false

    @Input
    @Optional
    int responseCacheTimeout = 300

    @Input
    @Optional
    String inSequence = ''

    @Input
    @Optional
    String outSequence = ''

    @Input
    @Optional
    String subscriptions = 'CURRENT_TENANT'

    @Input
    @Optional
    String tenants = ''

    @Input
    @Optional
    boolean httpChecked = true

    @Input
    @Optional
    boolean httpsChecked = true

    UpdateApiTask() {
        description 'creates an API if it does not already exist or updates the API if it does'
    }

    @Override
    String getApiName() {
        return apiName
    }

    @Override
    String getApiDescription() {
        return apiDescription
    }

    @Override
    List<String> getTags() {
        return CommaSeparatedStringConverter.toList(tags)
    }

    @Override
    List<ApiTierAvailability> getTiers() {
        List<String> values = CommaSeparatedStringConverter.toList(tiers)
        return ApiTierAvailability.toTiersList(values)
    }

    @Override
    String getEndpointConfig() {
        return endpointConfig
    }

    @Override
    String getSwagger() {
        return swagger
    }

    @Override
    String getContext() {
        return context
    }

    @Override
    String getThumbnailImageUrl() {
        return thumbnailImageUrl
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
    boolean isDefaultVersion() {
        return defaultVersion
    }

    @Override
    ApiVisibility getVisibility() {
        return ApiVisibility.parse(visibility)
    }

    @Override
    List<String> getRoles() {
        return CommaSeparatedStringConverter.toList(roles)
    }

    @Override
    ApiEndpointType getEndpointType() {
        return ApiEndpointType.parse(endpointType)
    }

    @Override
    String getEndpointUsername() {
        return endpointUsername
    }

    @Override
    String getEndpointPassword() {
        return endpointPassword
    }

    @Override
    boolean isResponseCacheEnabled() {
        return responseCacheEnabled
    }

    @Override
    int getResponseCacheTimeout() {
        return responseCacheTimeout
    }

    @Override
    String getInSequence() {
        return inSequence
    }

    @Override
    String getOutSequence() {
        return outSequence
    }

    @Override
    ApiSubscriptions getSubscriptions() {
        return ApiSubscriptions.parse(subscriptions)
    }

    @Override
    List<String> getTenants() {
        return CommaSeparatedStringConverter.toList(tenants)
    }

    @Override
    boolean isHttpChecked() {
        return httpChecked
    }

    @Override
    boolean isHttpsChecked() {
        return httpsChecked
    }

    @Override
    perform(ApiPublisherClient client) {
        DateTime updateTime = DateTime.now(UTC)
        createOrUpdateApi(client)
        verifyUpdate(client, updateTime)
    }

    private createOrUpdateApi(ApiPublisherClient client) {
        boolean exists = client.apiExists(apiName)
        log.info("api " + apiName + " exists " + exists)
        if (exists) {
            updateApi(client)
        } else {
            createApi(client)
        }
    }

    private updateApi(ApiPublisherClient client) {
        log.info("updating api " + apiName + " " + apiVersion)
        client.updateApi(this)
    }

    private createApi(ApiPublisherClient client) {
        log.info("creating api " + apiName + " " + apiVersion)
        client.addApi(this)
    }

    private verifyUpdate(ApiPublisherClient client, DateTime updateTime) {
        log.info("verifying update to api " + apiName + " " + apiVersion)
        Api api = client.getApi(this)
        DateTime lastUpdated = api.getLastUpdated()
        boolean complete = lastUpdated.isAfter(updateTime) || lastUpdated.isEqual(updateTime)
        log.info(api.getName() + " " + api.getVersion() + " last updated " + lastUpdated + " checking against update time " + updateTime + " update complete " + complete)
        if (!complete)
            throw new Wso2ApiPluginException("update not complete")
        log.info("successfully updated api " + api.getName() + " " + api.getVersion())
    }

}

