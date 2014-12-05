import sbt._
import Keys._

name := "HelloWorld"

lazy val common = project.in(file("lib/common"))

lazy val logging = project.in(file("lib/logging")).dependsOn(common)

lazy val etcd = project.in(file("lib/etcd")).dependsOn(common)

lazy val cluster = project.in(file("lib/cluster")).dependsOn(etcd, logging, common)

lazy val persistence = project.in(file("lib/persistence")).dependsOn(etcd, logging, common)

lazy val main = project.in(file("main")).dependsOn(common, cluster, logging, persistence)

lazy val root = project.in(file(".")).aggregate(main, common, etcd, logging, cluster, persistence)
