// gradle init --type=basic --dsl=kotlin --project-name=gradle-kotlin-dsl-plugin-buildSrc
plugins {
  id("com.moowork.node") version "1.2.0"
  postgres
  base
}

postgres {
  db = "my-db"
  user = "my-user"
  password = "secret"
  ports = "15432:5432"
  name = "testing"
}

defaultTasks("pgInfo")

node {
  download = true
  version = "10.9.0"
  npmVersion = "6.8.0"
}
tasks.create("start")
tasks["start"].dependsOn("npm_start")
tasks["build"].dependsOn("npm_run_build")

tasks.withType<Wrapper> {
  gradleVersion = "5.2.1"
  distributionType = Wrapper.DistributionType.BIN
}
