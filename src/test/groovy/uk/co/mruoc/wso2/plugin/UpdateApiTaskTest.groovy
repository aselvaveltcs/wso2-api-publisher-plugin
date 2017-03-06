package uk.co.mruoc.wso2.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.GenericContainer
import uk.co.mruoc.wso2.StartupCheckLogConsumer
import uk.co.mruoc.wso2.Wso2StartupCheckLogConsumer

class UpdateApiTaskTest {

    private static final String DOCKER_IMAGE = "michaelruocco/wso2am:1.9.1"
    private static final int PORT = 9443

    private final StartupCheckLogConsumer logConsumer = new Wso2StartupCheckLogConsumer()

    @Rule
    public final GenericContainer container = new GenericContainer(DOCKER_IMAGE)
            .withExposedPorts(PORT)
            .withLogConsumer(logConsumer)

    private urlBuilder = new UrlBuilder(container)
    private project = ProjectBuilder.builder().build()

    @Before
    void setUp() {
        logConsumer.waitForStartupMessageInLog()
    }

    @Test
    void shouldCreateApi() {
        configureExtension()

        def task = project.task('createApi', type: UpdateApiTask)
        task.apiName = "rest-product"
        task.apiDescription "product api"
        task.apiVersion = "v1"
        task.tags = "product"
        task.context = "/product/{version}"
        task.provider = "admin"
        task.endpointConfig = "{\"production_endpoints\": {\"url\":\"http://ws-uat1.dev.tppim.co.uk/pimwebservices/services/rest-taxonomy-entity-v1/taxonomy\", \"config\": null},\"endpoint_type\":\"http\"}"
        task.swagger = "{\"consumes\":[\"application/json\"],\"info\":{\"description\":\"rest-taxonomy : (build 20170103134850)\",\"title\":\"rest-taxonomy\",\"version\":\"v1\"},\"paths\":{\"/z20170103134850\":{\"get\":{\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}},\"/{catalog}/paged*\":{\"get\":{\"parameters\":[{\"description\":\"Catalog id (for ex. GroupMasterProductCatalog)\",\"in\":\"path\",\"name\":\"catalog\",\"required\":true,\"type\":\"string\"},{\"in\":\"query\",\"name\":\"limit\",\"required\":true,\"type\":\"integer\"},{\"in\":\"query\",\"name\":\"offset\",\"required\":true,\"type\":\"integer\"}],\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}}},\"produces\":[\"application/json\"],\"schemes\":[\"https\"],\"swagger\":\"2.0\"}"
        task.execute()
    }

    @Test
    void shouldUpdateApi() {
        configureExtension()

        def task = project.task('createApi', type: UpdateApiTask)
        task.apiName = "rest-product"
        task.apiDescription "product api"
        task.apiVersion = "v1"
        task.tags = "product"
        task.context = "/product/{version}"
        task.provider = "admin"
        task.endpointConfig = "{\"production_endpoints\": {\"url\":\"http://ws-uat1.dev.tppim.co.uk/pimwebservices/services/rest-taxonomy-entity-v1/taxonomy\", \"config\": null},\"endpoint_type\":\"http\"}"
        task.swagger = "{\"consumes\":[\"application/json\"],\"info\":{\"description\":\"rest-taxonomy : (build 20170103134850)\",\"title\":\"rest-taxonomy\",\"version\":\"v1\"},\"paths\":{\"/z20170103134850\":{\"get\":{\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}},\"/{catalog}/paged*\":{\"get\":{\"parameters\":[{\"description\":\"Catalog id (for ex. GroupMasterProductCatalog)\",\"in\":\"path\",\"name\":\"catalog\",\"required\":true,\"type\":\"string\"},{\"in\":\"query\",\"name\":\"limit\",\"required\":true,\"type\":\"integer\"},{\"in\":\"query\",\"name\":\"offset\",\"required\":true,\"type\":\"integer\"}],\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}}},\"produces\":[\"application/json\"],\"schemes\":[\"https\"],\"swagger\":\"2.0\"}"
        task.execute()

        task = project.task('updateApi', type: UpdateApiTask)
        task.apiName = "rest-product"
        task.apiDescription = "updated product api description"
        task.apiVersion = "v1"
        task.tags = "product"
        task.context = "/product/{version}"
        task.provider = "admin"
        task.endpointConfig = "{\"production_endpoints\": {\"url\":\"http://ws-uat1.dev.tppim.co.uk/pimwebservices/services/rest-taxonomy-entity-v1/taxonomy\", \"config\": null},\"endpoint_type\":\"http\"}"
        task.swagger = "{\"consumes\":[\"application/json\"],\"info\":{\"description\":\"rest-taxonomy : (build 20170103134850)\",\"title\":\"rest-taxonomy\",\"version\":\"v1\"},\"paths\":{\"/z20170103134850\":{\"get\":{\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}},\"/{catalog}/paged*\":{\"get\":{\"parameters\":[{\"description\":\"Catalog id (for ex. GroupMasterProductCatalog)\",\"in\":\"path\",\"name\":\"catalog\",\"required\":true,\"type\":\"string\"},{\"in\":\"query\",\"name\":\"limit\",\"required\":true,\"type\":\"integer\"},{\"in\":\"query\",\"name\":\"offset\",\"required\":true,\"type\":\"integer\"}],\"responses\":{\"200\":{}},\"x-auth-type\":\"Application\",\"x-throttling-tier\":\"Unlimited\"}}},\"produces\":[\"application/json\"],\"schemes\":[\"https\"],\"swagger\":\"2.0\"}"
        task.execute()
    }

    private configureExtension() {
        def extension = getExtension()
        extension.username = "admin"
        extension.password = "admin"
        extension.hostUrl = urlBuilder.build(PORT)
    }

    private getExtension() {
        project.extensions.create('apiPublisher', Wso2ApiPublisherExtension)
    }

}
