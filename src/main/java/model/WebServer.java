package model;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


public class WebServer {
  private int port;
  private HttpServer server;

  public WebServer(int port) {
    this.port = port;
  }

  public void start() throws Exception {
    server = HttpServer.create(new InetSocketAddress(port), 0);
    server.start();
    System.out.println("The server has started on port: " + port);
  }

  public void stop() {
    server.stop(0);
  }

  private void contextCreator(String path, RequestHandler handler, Function callback) {
    server.createContext(path, handler);
    handler.addCallBack(callback);
  }

  public void get(String path, Function<HttpExchange, String> callback) {
    RequestHandler handler = new RequestHandler("GET");
    contextCreator(path, handler, callback);
  }

  public void post(String path, Function<HttpExchange, String> callback) {
    RequestHandler handler = new RequestHandler("POST");
    contextCreator(path, handler, callback);
  }

  public void makeFolderAvailable(String folderPath) {
    String filePath = new File("").getAbsolutePath();
    String resoursesPath = filePath.concat(folderPath);
    List<String> filePaths = new ArrayList<>();
    try (Stream<Path> paths = Files.walk(Paths.get(resoursesPath))) {
      filePaths = paths
          .filter(Files::isRegularFile)
          .map(Path::toString)
          .map(path -> path.substring(resoursesPath.length(), path.length()))
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    filePaths.forEach(path -> get(path, exchange -> {
      System.out.println(path);
      HttpResponse res = new HttpResponse(exchange);
      Document doc = new Document(folderPath + path);
      res.addDocument(doc);
      return doc.toString();
    }));
  }
}