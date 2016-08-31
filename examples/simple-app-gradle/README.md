## Example project with Dynatrace Gradle Plugin

This project contains example usage of the Dynatrace Gradle Plugin.

### Prerequisites

Gradle plugin should be built or installed before running this project

### Running project

To build project using gradle wrapper, simply execute: `./gradlew build`.
To run tests with injected agent, execute: `./gradlew test`

### Running tasks

In order to run any Dynatrace Gradle Plugin tasks, execute `./gradlew TASKNAME` (e.g. `./gradlew DtEnableProfile`).
Every task usage is presented in `build.gradle` file.