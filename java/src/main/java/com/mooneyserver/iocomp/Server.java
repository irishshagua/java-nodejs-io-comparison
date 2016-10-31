package com.mooneyserver.iocomp;

import java.util.List;

import com.google.gson.Gson;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathTemplateHandler;
import io.undertow.util.Headers;

public class Server {

  private static final Gson GSON = new Gson();
  
  public static void main(String... args) {
    PathTemplateHandler handler = new PathTemplateHandler();

    handler.add("/", Server::landingPage);
    handler.add("/pubs", Server::getAllPubs);
    handler.add("/pubs/{id}", Server::getPubById);

    Undertow server = Undertow.builder()
            .addHttpListener(8080, "0.0.0.0")
            .setHandler(handler)
            .build();

    server.start();
  }

  private static void landingPage(final HttpServerExchange exchange) {
    exchange.getResponseSender().send("Answered by Java");
  }

  
  private static void getAllPubs(final HttpServerExchange exchange) {
    if (exchange.isInIoThread()) {
      exchange.dispatch(() -> {
        List<Pub> pubs = new DBQuery().getAllPubs();
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(GSON.toJson(pubs));
      });
    }
  }

  private static void getPubById(final HttpServerExchange exchange) {
    if (exchange.isInIoThread()) {
      exchange.dispatch(() -> {
        List<Pub> pubs = new DBQuery().getPubById(getPathParam("id", exchange));
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(GSON.toJson(pubs));
      });
    }
  }
    
  private static String getPathParam(String paramName, HttpServerExchange exchange) {
      return exchange.getQueryParameters().get(paramName).getFirst();
  }
}
