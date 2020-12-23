load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.3"
RULES_JVM_EXTERNAL_SHA = "d85951a92c0908c80bd8551002d66cb23c3434409c814179c0ff026b53544dab"
RULES_JAVA_WAR_TAG = "0.1.0"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

http_archive(
    name = "io_bazel_rules_java_war",
    strip_prefix = "rules_java_war-%s" % RULES_JAVA_WAR_TAG,
    url = "https://github.com/bmuschko/rules_java_war/archive/%s.tar.gz" % RULES_JAVA_WAR_TAG,
    sha256 = "38011f979713c4aefd43ab56675ce4c6c14bc949b128c3a303f1f57ebe4bfeac",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.google.inject.extensions:guice-servlet:3.0",
        "com.google.inject:guice:3.0",
        "javax.inject:javax.inject:1",
        "javax.servlet:javax.servlet-api:4.0.1",
        "com.google.cloud:google-cloud-datastore:1.105.3"
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)