# article-summarizer

## Requirements to run this app
You need a local [Ollama](https://ollama.com) instance running

## Run the app
To run this application run the following commands:
1. `./mvnw clean package -DskipTests`
2. `java -jar target/quarkus-app/quarkus-run.jar <some_url>` where `<some_url>` is the URL you'd like to summarize (https://quarkus.io/guides/maven-tooling for example)