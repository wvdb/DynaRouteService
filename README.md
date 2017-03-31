# DynaRouteService

## History

This project can be found at http://gitlab.dev.yelo.inet.telenet.be/yellow-belt/provisioning-service.
It is created as a fork from the "Yelo / Yelo ProvisioningService" project. The front side will be
nearly identical (receive requests from IT/ASAP), but with backends defined by the LG BLueprint.

See http://gitlab.dev.yelo.inet.telenet.be/yelo/yelo-provisioningservice for the original repository.

## Getting started

A maven dependency (for project yelo-code-quality) cannot be resolved through Nexus. You'll have to install the
dependency manually before building.

Install
http://nexus.dev.yelo.inet.telenet.be/content/repositories/yelo-releases/be/telenet/yelo/yelo-code-quality/1.1.0/ by
* downloading the jar and the pom in a seperate folder
* Invoking the following command in that folder:
  ```bash
  mvn install:install-file -Dfile=yelo-code-quality-1.1.0.jar -DpomFile=yelo-code-quality-1.1.0.pom
  ```

# Migration to Spring-Boot

TVProvisioning was written in Spring, but not Spring-Boot. The requirement to deploy using the Spring-Boot internal server, needs a migration to Spring-Boot.

There are some issues with versions (especially Hibernate), but they have been fixed in this pom.xml. The main issue remains the Yelo Framework included in TVP. There a re still some issues making Yelo and SpringBoot talk to eachother.

To start this build, run :

mvn spring-boot:run -Dspring.profiles.active=dev


## Running locally

### Prerequisites

You need Docker. That's it. Really.
This document assumes that you are using a version of Docker that doesn't require docker-machine. If this is not the
case, you'll have to replace "localhost" with the IP address of your docker-machine in the explanations below.

One side note in case you already have SoapUI installed on your local machine: you'll need to disable the plugins
that were installed as part of SoapUI. Follow these steps to do so:

* Go to `~/.soapuios`
* Rename the `plugin` directory to `plugins_NOTinUse`

Reference: https://community.smartbear.com/t5/SoapUI-Open-Source/AutoDiscoveryMethodFactory-ClassNotFoundException-when-executing/td-p/105397

### Instructions

* build the package without running the tests : "mvn install -Dmaven.test.skip=true"
* run "docker-compose up" to start the environment
* build again with test "mvn install"


* Open a terminal and navigate to the project root directory
* Type the following command:
  ```
  docker-compose up --build --remove-orphans
  ```
* This will run two Docker containers:
  * one with the MySQL database
  * another with a Tomcat web container

### Accessing the database

The database is accessible on `jdbc:mysql://localhost:3306/provisioning`.

* user id: `provisioning`
* password: `provisioning`

### Accessing the Tomcat web container

The Tomcat container is mapped on http://localhost:8080. It starts in debug mode, allowing for remote debugging
on port 8000.

## Testing

The project contains a SoapUI project under `src/test/resources/soapui.xml`. At the time of writing, SoapUI v5.3.0
(free edition) was used. Start it to run tests against a running Docker instance of provisioning-service.

For the Mac OS users, install SoapUI with brew:
  ```
  brew cask install soapui
  ```

## Other documentation

Please see [TV Provisioning](https://bugtracker.inet.telenet.be/confluence/display/YB/Provisioning+Service)
Confluence page for more documentation (ex. batch processing).


