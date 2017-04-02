package uk.co.mruoc.wso2.plugin

import groovy.util.logging.Slf4j
import uk.co.mruoc.wso2.Credentials
import uk.co.mruoc.wso2.publisher.ApiPublisherClient
import uk.co.mruoc.wso2.publisher.DefaultApiPublisherClient
import uk.co.mruoc.wso2.publisher.InsecureDefaultApiPublisherClient

@Slf4j
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
        log.info("setting ssl enabled " + sslEnabled)
        this.sslEnabled = sslEnabled
    }

    Credentials getCredentials() {
        return new Credentials(username, password)
    }

    ApiPublisherClient getPublisherClient() {
        if (sslEnabled) {
            log.info("creating default api publisher client")
            return new DefaultApiPublisherClient(hostUrl)
        }
        log.info("creating INSECURE default api publisher client")
        return new InsecureDefaultApiPublisherClient(hostUrl)
    }

}