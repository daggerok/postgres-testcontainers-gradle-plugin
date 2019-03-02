# postgres-testcontainers-gradle-plugin
Postgres TestContainers internal (located in `buildSrc`) Gradle plugin, written in Kotlin using Gradle Kotlin DSL

- Travis CI [![Build Status](https://travis-ci.org/daggerok/postgres-testcontainers-gradle-plugin.svg?branch=master)](https://travis-ci.org/daggerok/postgres-testcontainers-gradle-plugin)
- GitHub [Pages](https://daggerok.github.io/postgres-testcontainers-gradle-plugin/) documentation
- GitHub [daggerok/postgres-testcontainers-gradle-plugin](https://github.com/daggerok/postgres-testcontainers-gradle-plugin) repository 

## plugin usage

```kotlin
plugins {
  postgres
}

postgres {
  db = "my-db"
  user = "my-user"
  password = "secret"
  ports = "15432:5432"
  name = "testing"
}
```

_pgInfo_

```bash
./gradlew pgInfo
# output
> Task :pgInfo
PostgresConfig(db=my-db, user=my-user, password=secret, ports=15432:5432, name=testing)
```

_pgStart_

```bash
./gradlew pgStart
# output
> Task :pgStart
started a49df6f25e1ab58207aac6da855223473d440b752d21e87dc1fa1f612c67aa08 as testing
```

_pgStop_

```bash
./gradlew pgStop
# output
> Task :pgStop
a49df6f25e1a
```

## resources

- [TestContainers: making Java integration tests easy](https://zeroturnaround.com/rebellabs/java-integration-testing-with-testcontainers/)
- [Writing Custom Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html)
- [Gradle Kotlin DSL samples](https://github.com/gradle/kotlin-dsl/tree/master/samples)
- [Gradle Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin)
- [Gradle Tutorials](https://gradle.org/guides/)
