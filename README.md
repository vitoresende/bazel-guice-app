# Bazel Guice App
This project aims to be a **Quick Start** and/or help those who wants to start using these technologies. 

### Bazel
Bazel is an open-source build and test tool similar to Make, Maven, and Gradle. It uses a human-readable, high-level build language. Bazel supports projects in multiple languages and builds outputs for multiple platforms. [See more about Bazel here](https://docs.bazel.build/versions/master/bazel-overview.html) and [Bazel code examples here](https://github.com/bazelbuild/bazel).
- Installing on [Windows](https://docs.bazel.build/versions/master/install-windows.html
  ). For this project and how it's described in this document, it's mandatory installing   ). For this project and how it's described in this document, it's mandatory installing **MSYS2 x86_64**
and **Java SE Development Kit 11 (JDK) for Windows x64**.
  
- Installing on [Linux](https://docs.bazel.build/versions/master/install-ubuntu.html).

To install in Linux, I did it using Linux binary:
1)  Install required packages
2) download the Bazel binary installer named bazel-<version>-installer-linux-x86_64.sh from the Bazel [releases page on GitHub](https://github.com/bazelbuild/bazel/releases).
```
chmod +x bazel-<version>-installer-linux-x86_64.sh
./bazel-<version>-installer-linux-x86_64.sh --user
```
3) Set up your environment
```
export PATH="$PATH:$HOME/bin"
```
### Guice
Guice aims to make development and debugging easier and faster, not harder and slower. [See more about Guice here](https://github.com/google/guice).

---

## Application
This application was thought to be deployed using GCP. So, it's necessary install gcloud SDK following this [guide](https://cloud.google.com/sdk/docs/quickstart).

### Running locally
Building our application will generate a .war file that could be served using Apache Tomcat, for instance.

It's possible to download Tomcat [here](https://tomcat.apache.org/download-80.cgi), and then, update the web-app.war file in the webapps folder with our .war generated by the application.

We can though, access the Hello World page from **http://localhost:8080/web-app/** and the other functionality is to save in Datastore the body request (it'll only work on GCP).

e.g.:
```json
POST https://<appengine-url>.appspot.com/
{
  "description": "Test"
}
```
We can see, though, the body saved in GCP Datastore.
![GCP Data](readme/gcp-datastore.png)

### Build
```
bazel build //:web-app
```

### Deploy
```
gcloud app deploy app.yaml
```

## Troubleshooting

#### Cannot find Java binary
When having this kind of problems, either correct your JAVA_HOME, PATH or specify embedded Java (e.g. --javabase=@bazel_tools//tools/jdk:remote_jdk11).

```
bazel build //:web-app --javabase=@bazel_tools//tools/jdk:remote_jdk11
```

## Running directly on Gcloud

You can edit and run this application directly from GCP.

To deploy it, access your Gcloud environment through Gcloud SDK or Gcloud Shell and clone this repository into it.

Then use the same commands described before to build and deploy the application.

But, before running the Deploy command, copy the war file to the root of application.

The following commands should deploy the application once you're logged in Gcloud SDK or Gcloud Shell:
```
git clone https://github.com/vitoresende/bazel-guice-app.git
cd bazel-guice-app/
bazel build //:web-app
cp bazel-bin/web-app.war ./
gcloud app deploy
```
