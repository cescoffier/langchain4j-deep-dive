# Quarkus AI Service - Auditing

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

## Running this app
1. Run `./mvnw clean quarkus:dev`

> [!NOTE]
> It may take a minute or so for the observability stack to start up 

2. Once the app is up, hit the `d` key in the Quarkus console, or open a browser to http://localhost:8080/q/dev-ui/
3. Find the `SmallRye OpenAPI` tile and click the `Swagger UI` link (http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui)
4. Expand the `/nba` entry and click `Try it out`
5. Enter `{"player": "Michael Jordan"}` in the `Request body` section and click `Execute`
6. You should see a response in the `Response body` section
7. If you examine the console logs you should see log messages that were printed as various audit events happened:

   ```
   INFO  [org.acm.AuditListener] (executor-thread-1) Initial messages:
   source: AuditSourceInfo{interactionId=387fd325-1039-467a-b69e-03efea94e9f4, interfaceName='org.acme.Assistant', methodName='ask', memoryIDParamPosition=Optional.empty, methodParams=[Question[player=Michael Jordan]]}
   systemMessage: Optional[SystemMessage { text = "You are a useful AI assistant expert in NBA." }]
   userMessage: UserMessage { name = null contents = [TextContent { text = "    What are the last team in which Michael Jordan played?
    Return the team and the last season.

    You must answer strictly in the following JSON format: {
      "team": (type: string),
      "years": (type: string)
    }" }] }
   
   INFO  [org.acm.AuditListener] (executor-thread-1) Response from LLM received:
   source: AuditSourceInfo{interactionId=387fd325-1039-467a-b69e-03efea94e9f4, interfaceName='org.acme.Assistant', methodName='ask', memoryIDParamPosition=Optional.empty, methodParams=[Question[player=Michael Jordan]]}
   response: {
    "team": "Washington Wizards",
    "years": "2001-2003"
    }

   INFO  [org.acm.AuditListener] (executor-thread-1) LLM interaction complete:
   source: AuditSourceInfo{interactionId=387fd325-1039-467a-b69e-03efea94e9f4, interfaceName='org.acme.Assistant', methodName='ask', memoryIDParamPosition=Optional.empty, methodParams=[Question[player=Michael Jordan]]}
   result: Entry[team=Washington Wizards, years=2001-2003]
   ```

8. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`