package uk.co.mruoc.wso2.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class Wso2ApiPublisherPluginTest {

    private Project project = ProjectBuilder.builder().build()

    @Test
    void addsEmbeddedMysqlExtensionToProject() {
        applyPluginToProject()
        assertThat(project.extensions.apiPublisher instanceof Wso2ApiPublisherExtension).isTrue()
    }

    @Test
    void addsUpdateApiTaskToProject() {
        applyPluginToProject()
        assertThat(project.tasks.updateApi instanceof UpdateApiTask).isTrue()
    }

    @Test
    void addsSetStatusApiTaskToProject() {
        applyPluginToProject()
        assertThat(project.tasks.setApiStatus instanceof SetApiStatusTask).isTrue()
    }

    @Test
    void addsRemoveApiTaskToProject() {
        applyPluginToProject()
        assertThat(project.tasks.removeApi instanceof RemoveApiTask).isTrue()
    }

    private void applyPluginToProject() {
        project.plugins.apply(Wso2ApiPublisherPlugin)
    }

}

