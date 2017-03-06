package uk.co.mruoc.wso2.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class ApiPublisherPluginTest {

    private Project project = ProjectBuilder.builder().build()

    @Test
    void addsEmbeddedMysqlExtensionToProject() {
        applyPluginToProject()
        assertThat(project.extensions.apiPublisher instanceof Wso2ApiPublisherExtension).isTrue()
    }

    private void applyPluginToProject() {
        project.plugins.apply(ApiPublisherPlugin)
    }

}

