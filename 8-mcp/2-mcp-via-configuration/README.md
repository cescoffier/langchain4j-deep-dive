# Using tools with an MCP server from NPM with vanilla LangChain4j

## Requirements for running this app
By default this app uses OpenAI's gpt-4o model.

You need to set the `OPENAI_API_KEY` environment variable to the value of your OpenAI key.

This app also requires the `npm` package manager installed and present on the `$PATH` variable.
It is assumed that a NPM server can be bootstrapped by executing
`npm -y exec @modelcontextprotocol/server-filesystem@0.6.2 playground` in the root directory of this project.
If the command needs to be altered, do that manually in `application.properties`. 

## Running this app
1. Run `./mvnw clean quarkus:dev`
2. See the output in the console
3. Check that a file `playground/sum.py` was created with a Python script that sums two numbers
4. When done, hit the `q` key in the Quarkus console

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `qwen2.5-coder:14b` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`