import model.Document;
import model.HttpResponse;
import model.WebServer;

public class Main {

  public static void main(String[] args) throws Exception {
    int port = 3000;
    WebServer server = new WebServer(port);
    server.start();
    String resources = "/src/main/resources";
    server.makeFolderAvailable(resources);
    Document html = new Document(resources + "/index.html");

    server.get("/", exchange -> {
      HttpResponse response = new HttpResponse(exchange);
      response.addDocument(html);
      return html.toString();
    });
  }
}