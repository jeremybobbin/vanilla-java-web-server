package model;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;


class RequestHandler implements HttpHandler {
  private String httpVerb;
  private Function<HttpExchange, String> callback;

  public RequestHandler(String httpVerb) {
    this.httpVerb = httpVerb;
  }

  public void addCallBack(Function callback) {
    this.callback = callback;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    //Check if verb matches request

    String response = callback.apply(exchange);
    if (!httpVerb.equals(exchange.getRequestMethod())) {
      exchange.sendResponseHeaders(404, response.length());
    } else {
      exchange.sendResponseHeaders(200, response.length());
    }

    try (OutputStream os = exchange.getResponseBody()) {
      os.write(response.getBytes());
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
}