load("@rules_java//java:defs.bzl", "java_binary")
load("@io_bazel_rules_java_war//java_war:defs.bzl", "java_war")

java_war(
    name = "web-app",
    java_srcs = glob(["src/main/java/**/*.java"]),
    deps = [
        "@maven//:com_google_inject_extensions_guice_servlet",
        "@maven//:com_google_inject_guice",
        "@maven//:javax_inject_javax_inject",
        "@maven//:javax_servlet_javax_servlet_api",
        "@maven//:com_google_cloud_google_cloud_datastore",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_flogger_flogger",
        "@maven//:com_google_flogger_flogger_system_backend"
    ],
)