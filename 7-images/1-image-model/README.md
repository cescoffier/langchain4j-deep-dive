# Image models

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. See the output in the console
3. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` and `llava:7b` models are pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`