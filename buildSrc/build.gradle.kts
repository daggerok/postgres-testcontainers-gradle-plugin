plugins {
  `kotlin-dsl`
  java
}

configure<KotlinDslPluginOptions> {
  experimentalWarning.set(false)
}

// require
repositories {
  jcenter()
}

dependencies {
  implementation("org.testcontainers:postgresql:1.10.6")
}

gradlePlugin {
  plugins {
    register("postgres-plugin") {
      id = "postgres"
      implementationClass = "com.github.daggerok.plugin.PostgresPlugin"
    }
  }
}
