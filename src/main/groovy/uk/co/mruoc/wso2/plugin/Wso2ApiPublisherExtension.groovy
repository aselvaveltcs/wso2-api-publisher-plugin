package uk.co.mruoc.wso2.plugin

import uk.co.mruoc.http.client.SimpleHttpClient
import uk.co.mruoc.http.client.insecure.InsecureHttpClientFactory
import uk.co.mruoc.wso2.Credentials
import uk.co.mruoc.wso2.publisher.ApiPublisherClient
import uk.co.mruoc.wso2.publisher.DefaultApiPublisherClient

class Wso2ApiPublisherExtension {

    private static final DEFAULT_USERNAME = "admin"
    private static final DEFAULT_PASSWORD = "admin"
    private static final DEFAULT_HOST_URL = "https://localhost:9443"
    private static final DEFAULT_SSL_ENABLED = true

    private username = DEFAULT_USERNAME
    private password = DEFAULT_PASSWORD
    private hostUrl = DEFAULT_HOST_URL
    private sslEnabled = DEFAULT_SSL_ENABLED

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getHostUrl() {
        return hostUrl
    }

    void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl
    }

    boolean getSslEnabled() {
        return sslEnabled
    }

    void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled
    }

    Credentials getCredentials() {
        return new Credentials(username, password)
    }

    ApiPublisherClient getPublisherClient() {
        if (sslEnabled)
            return new DefaultApiPublisherClient(hostUrl)
        return new DefaultApiPublisherClient(new SimpleHttpClient(InsecureHttpClientFactory.build()), hostUrl)
    }

}