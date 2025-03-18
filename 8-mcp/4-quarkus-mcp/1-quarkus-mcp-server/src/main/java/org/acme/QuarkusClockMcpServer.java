package org.acme;


import io.quarkiverse.mcp.server.Tool;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;

public class QuarkusClockMcpServer {

    @Tool(description = "Give the current time")
    public String time() {
        ZonedDateTime now = ZonedDateTime.now();
        return now.toLocalTime().format(new DateTimeFormatterBuilder().appendPattern("HH:mm:ss").toFormatter());
    }

}
