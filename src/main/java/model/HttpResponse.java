package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

public class HttpResponse{
  Map<String, List<String>> header;

  public HttpResponse(HttpExchange httpExchange) {
    header = httpExchange.getResponseHeaders();
  }

  public void addHeader(String key, String value) {
    if (header.containsKey(key)) {
      header.get(key).add(value);
    } else {
      List<String> newList = new ArrayList<>();
      newList.add(value);
      header.put(key, newList);
    }
  }


  public void addDocument(Document doc) {
    addHeader("Content-Type", doc.getFileType());
  }
}
