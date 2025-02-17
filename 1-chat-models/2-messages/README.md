# messages

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. Once the app is up you will see the output on the console
3. In [`Messages.java`](src/main/java/org/acme/Messages.java) comment out `var system = new SystemMessage("You are Georgios, all your answers should be using the Java language using greek letters");` and uncomment the following line
4. Return to the Quarkus console and hit the space bar
5. You should see the app re-run with the updated system message
6. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`