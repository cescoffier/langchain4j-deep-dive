# chatbot

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. Once the app is up, hit the `w` key in the Quarkus console, or open a browser to http://localhost:8080
3. Click the chat icon on the bottom-right of the screen
4. Chat with the bot (i.e. Tell it that you are `Speedy McWheels` and you want it to list your bookings)
5. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`