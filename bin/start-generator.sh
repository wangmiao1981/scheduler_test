#!/usr/bin/env bash


echo " ==========  Compiling code and generating .jar ============"
sbt compile
sbt 'project spark-test-generator' compile package assembly

sbt 'project spark-test-generator' run