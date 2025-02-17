# Quarkus AI Service - Custom memory

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. Once the app is up, hit the `d` key in the Quarkus console, or open a browser to http://localhost:8080/q/dev-ui/
3. Find the `SmallRye OpenAPI` tile and click the `Swagger UI` link (http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui)
4. Expand the `/memory` entry and click `Try it out`
5. Enter `My name is Clement` in the `Request body` section and `1` in the `id` field, then click `Execute`
6. You should see a response in the `Response body` section
7. Try it again with `My name is Georgios` in the `Request body` and `2` in the `id` field
8. Change the `Request body` field to be `What is my name?`
9. It should say your name is Georgios
10. Change the `id` field to `1` and click `Execute`
11. It should say your name is Clement
12. Change the `id` field to `3` and click `Execute`
13. It should say it doesn't know your name
14. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`