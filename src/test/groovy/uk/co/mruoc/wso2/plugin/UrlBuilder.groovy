package uk.co.mruoc.wso2.plugin

import org.testcontainers.containers.Container

class UrlBuilder {

    private static final String URL = "https://%s:%d";

    private Container container

    UrlBuilder(Container container) {
        this.container = container
    }

    String build(int port) {
        return String.format(URL, container.getContainerIpAddress(), container.getMappedPort(port));
    }

}
