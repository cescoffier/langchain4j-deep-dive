# ocr-my-writing

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. Once the app is up, hit the `d` key in the Quarkus console, or open a browser to http://localhost:8080/q/dev-ui/
3. Find the `SmallRye OpenAPI` tile and click the `Swagger UI` link (http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui)
4. Expand the `/ocr` entry and click `Try it out`
5. In the `Request body` section click the `Choose File` button
6. Find the [`src/main/resources/images/handwritten_text.jpg`](src/main/resources/images/handwritten_text.jpg) file in the file browser and select it
7. Click the `Execute` button
8. In the `Response body` section you should see something like this
   ```
   Here is the extracted text from the image:

   ---

   This is some text!  
   My parents always said I was going to be a doctor!  

   Qu'en pensez-vous? Mon écriture est-elle bonne ou pas?  

   --- 
   ```

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llava:7b` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`

Note, the response you get back in step 8 is probably very different.
