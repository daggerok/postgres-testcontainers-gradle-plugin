package com.github.daggerok.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.create
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.PostgreSQLContainerProvider
import java.util.function.Consumer

const val POSTGRES_DB = "POSTGRES_DB"
const val POSTGRES_USER = "POSTGRES_USER"
const val POSTGRES_PASSWORD = "POSTGRES_PASSWORD"
const val POSTGRES_PORTS = "POSTGRES_PORTS"
const val POSTGRES_NAME = "POSTGRES_NAME"

fun String.getEnvOrSystemOrElse(defaultValue: String) =
    System.getenv().getOrDefault(this, System.getProperty(
        this.toLowerCase().replace("_", "."), defaultValue))!!

data class ToStringData(val db: String,
                        val user: String,
                        val password: String,
                        val ports: String,
                        val name: String)

open class PostgresConfig(var db: String = POSTGRES_DB.getEnvOrSystemOrElse("postgres"),
                          var user: String = POSTGRES_USER.getEnvOrSystemOrElse("postgres"),
                          var password: String = POSTGRES_PASSWORD.getEnvOrSystemOrElse("postgres"),
                          var ports: String = POSTGRES_PORTS.getEnvOrSystemOrElse("5432:5432"),
                          var name: String = POSTGRES_NAME.getEnvOrSystemOrElse("postgres")) {

  override fun toString() = ToStringData(db, user, password, ports, name).toString()
      .replaceFirst(ToStringData::class.java.simpleName, PostgresConfig::class.java.simpleName)

  internal fun startPostgres() {
    val container = PostgreSQLContainer("postgres:11.2-alpine")
    container.withDatabaseName(db)
    container.withUsername(user)
    container.withPassword(password)
    container.setPortBindings(listOf(ports))
    container.withLabels(mapOf("MAINTAINER" to "Maksim Kostromin https://github.com"))
    container.withCreateContainerCmdModifier(Consumer {
      it.withName(name)
    })
    container.start()
    println("started ${container.getContainerId()} as $name")
  }
}

class PostgresPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit = target.run {

    val postgres = extensions.create<PostgresConfig>("PostgresConfig")
    extensions.add("postgres", postgres)

    tasks.register("pgInfo") {
      doLast {
        println(postgres)
      }
    }

    tasks.register("pgStart") {
      doLast {
        postgres.startPostgres()
      }
    }

    tasks.register("pgStop", Exec::class.java) {
      executable = "bash"
      setArgs(listOf("-c", "for i in \$(docker ps -aq -f name='${postgres.name}|testcontainers') ; do docker rm -v -f \$i ; done"))
    }
  }
}
