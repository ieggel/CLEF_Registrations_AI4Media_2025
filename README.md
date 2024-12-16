# CLEF_Registrations_Gforms_2023


## Useful organizational info

### ImageCLEF Tasks for 2025

Task 1 - ImageCLEFmedical
Task 2 - Image Retrieval/Generation for Arguments => Also used by touché track, so they will probably not use our registration/submission
Task 3 - ImageCLEFtoPicto
Task 4 - MultimodalReasoning


## Useful technical info

### Lombok
Lombok must also be installed for eclipse itself: https://projectlombok.org/setup/eclipse

### Gradle
Gradle is used to run the project and to handle dependencies.

When using Gradle 8.0+, the `mainClassName` property should be modified as follows:
```gradle
application {
    mainClass = <class_name>
}
```
This has already been done for this project, so you can safely ignore this info.

#### Gradle wrapper
Make sure the gradlew version is compatible with the java version used

You can update the gradle wrapper with `./gradlew wrapper --gradle-version <version_num> `.
This will also change the version in `./gradle/wapper/gradle-wrapper.properties` which will be used to download the 
wrapper when none is present yet.

### HttpClient
https://www.baeldung.com/java-9-http-client

### Source & Target JVM compatibility in Gradle
https://www.baeldung.com/gradle-sourcecompatiblity-vs-targetcompatibility

### Tests
Junit5 feature TestInstance(Lifecycle.PER_CLASS) only creates one instance for multiple test method calls in the same class. 
More info here: https://www.baeldung.com/junit-testinstance-annotation

If using eclipse and wanting the ability to run tests with eclipse, make sure the version used by gradle (build.gradle) 
is the same used by eclipse itself, otherwise 2 gradle versions might be present, creating conflicts.


# TODO
- TEST REAL REGISTRATION FORM
- TELL NICCOLA TO DELETE THE FOLLOWING ENTRIES IN THE CLEF REGISTRATIONS:
  - stefan.liviu.daniel@gmail.com
  - stanciu.cristi12@gmail.com
  - agentili@ucsd.edu
  - andrei.alexandra96@yahoo.com
