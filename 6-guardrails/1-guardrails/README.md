# Guardrails

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. See the output in the console
3. You can comment/uncomment lines in [`GuardrailApplication.java`](src/main/java/org/acme/GuardrailApplication.java) to see the application fail due to guardrails being invoked

> [!NOTE]
> Use the space bar in the Quarkus console to refresh the app after making a change

4. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`