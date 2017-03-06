package uk.co.mruoc.wso2.plugin

import uk.co.mruoc.wso2.Credentials

class Wso2ApiPublisherExtension {

    private static final DEFAULT_USERNAME = "admin"
    private static final DEFAULT_PASSWORD = "admin"
    private static final DEFAULT_HOST_URL = "https://localhost:9443";

    private username = DEFAULT_USERNAME
    private password = DEFAULT_PASSWORD
    private hostUrl = DEFAULT_HOST_URL

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

    Credentials getCredentials() {
        return new Credentials(username, password)
    }

}