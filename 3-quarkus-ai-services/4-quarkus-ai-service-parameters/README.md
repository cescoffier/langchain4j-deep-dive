# Quarkus AI Service - Parameters and Structured Output

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. Once the app is up, hit the `d` key in the Quarkus console, or open a browser to http://localhost:8080/q/dev-ui/
3. Find the `SmallRye OpenAPI` tile and click the `Swagger UI` link (http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui)
4. Expand the `/nba` entry and click `Try it out`
5. Enter `{"player": "Michael Jordan"}` in the `Request body` section and click `Execute`
6. You should see a response in the `Response body` section
7. Expand the `/nba-last` entry and click `Try it out`
8. Enter `{"player": "Michael Jordan"}` in the `Request body` section and click `Execute`
9. You should see a response in the `Response body` section
10. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`