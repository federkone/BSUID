# BSUID

[![Maven Central](https://img.shields.io/maven-central/v/io.github.federkone/BSUID)](https://central.sonatype.com/artifact/io.github.federkone/BSUID)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
![Java 25](https://img.shields.io/badge/Java-25-orange)

A tiny, dependency-free Java utility that checks whether a `String` is a valid
**BSUID (Business-Scoped User ID)** as used by the **Meta WhatsApp Business Platform**.
## Requirements

- Java 25 or later

## Installation

**Maven**

```xml
<dependency>
  <groupId>io.github.federkone</groupId>
  <artifactId>BSUID</artifactId>
  <version>1.1.0</version>
</dependency>
```

**Gradle (Kotlin DSL)**

```kotlin
implementation("io.github.federkone:BSUID:1.1.0")
```

**Gradle (Groovy DSL)**

```groovy
implementation 'io.github.federkone:BSUID:1.1.0'
```

## Usage

```java
import io.github.federkone.bsuid.BSUID;

BSUID bsuid = BSUID.fromString("US.13491208655302741918");
System.out.println(bsuid);

String region = bsuid.region();
String id = bsuid.id();

//check if a bsuid is parent version
boolean isParent = bsuid.isParent();

//generate random BSUID
BSUID randomId = BSUID.randomBSUID();

//check if a string es a bsuid
boolean itIsAnBSUID = BSUID.itIsAnBSUID("US.13491208655302741918");
```

Invalid input is rejected at creation time:

```java
try {
    BSUID.fromString("123145124155");
} catch (InvalidBSUIDException e) {
    System.err.println(e.getMessage());
}
```

## Contributing

Issues and pull requests are welcome. 

## License

Released under the [MIT License](LICENSE).
