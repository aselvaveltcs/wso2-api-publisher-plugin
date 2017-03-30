# WSO2 API Manager Publisher Plugin

[![Build Status](https://travis-ci.org/michaelruocco/wso2-api-publisher-plugin.svg?branch=master)](https://travis-ci.org/michaelruocco/wso2-api-publisher-plugin)
[![Coverage Status](https://coveralls.io/repos/github/michaelruocco/wso2-api-publisher-plugin/badge.svg?branch=master)](https://coveralls.io/github/michaelruocco/wso2-api-publisher-plugin?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.michaelruocco/wso2-api-publisher-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.michaelruocco/wso2-api-publisher-plugin)

This plugin is a wrapper around the [WSO2 Api Manager Client](https://github.com/michaelruocco/wso2-api-manager-client)
library that allows deployment of APIs directly from a gradle script.

## Usage

To use the plugin's functionality, you will need to add the its binary artifact to your build script's
classpath and apply the plugin.

### Adding the plugin binary to the build

The plugin JAR needs to be defined in the classpath of your build script. It is directly available on
[Maven Central](http://search.maven.org/).
The following code snippet shows an example on how to retrieve it from Bintray:

```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.michaelruocco:wso2-api-publisher-plugin:1.0.2'
    }
}
```

### Applying the plugin

To use the plugin, include the following code snippet in your build script:

```
apply plugin: 'wso2-api-publisher'
```

This will add three new tasks to your gradle build:

* updateApi
* setApiStatus
* removeApi

### Configuring your API Manager Instance

It should be fairly obvious what these two tasks do, before you can use them you will need to
configure the plugin to point at your API manager instance, you do this by providing properties to the
Wso2ApiPublisherExtension as shown below.

```
apiPublisher {
    username = 'admin'
    password = 'admin'
    hostUrl = 'http://localhost:9443'
    sslEnabled = true   //true is the default value so this always on, but it can be useful to turn off for testing
}
```

Once you have set these properties the plugin will be able to find the publisher APIs on your API
Manager instance and login and out of the publisher. Note - if your API Manager instance is not running
on the local machine on the default port of 9443 and is not using the default username and password
of admin admin then these values will need to be changed.

#### Default configuration

The default values for each of the underlying properties are:

* username = 'admin'
* password = 'admin' (empty string, this means no password is required to access your embedded MySQL database)
* hostUrl = 'http://localhost:9443'

This means the example configuration above will be set by default, so there is not need to
explicitly set each of those values.

### Creating / Updating an API

The updateApi task will create an API if it does not already exist, or update an existing API if it already exists.
On top of the default parameters set against the API publisher extension as outlined above, there are some
other API specific properties that need to be set in order to create or update an API.

```
updateApi {
    apiName = 'my-api'
    apiVersion = 'v1'
    provider = 'admin'

    adiDescription = 'My API description'
    tags = 'My, API'
    context = '/my-api'

    endpointConfig = '"{\"someJsonConfig\": {\"key1\": \"value1\"}}"'
    swagger = '"{\"someMoreJsonConfig\": {\"key1\": \"value1\"}}"'
}
```

### Setting API Status

The setApiStatus task will set the status of an existing API.

```
setApiStatus {
    apiName = 'my-api'
    apiVersion = 'v1'
    provider = 'admin'

    apiStatus = 'PUBLISHED'
}
```

### Removing an API

The removeApi will remove an existing API.

```
removeApi {
    apiName = 'my-api'
    apiVersion = 'v1'
    provider = 'admin'
}
```

## Running the tests

This project is covered with integration and unit tests. To run the tests you can run the command:

```
gradlew clean build
```

### Running the tests from IDE

If you are trying to run the tests directly in your IDE rather
than using the gradle tasks provided then you will need to set the JVM
argument to point at the truststore provided in the project at:

```
{projectDir}/trustore/cacerts
```

For example:

```
-Djavax.net.ssl.trustStore=/Users/michaelruocco/git/github/wso2-api-publisher-plugin/truststore/cacerts
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```
gradlew dependencyUpdates
```