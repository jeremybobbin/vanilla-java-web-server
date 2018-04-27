package model;

import static java.nio.file.Files.probeContentType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Document {
  private String url;

  public Document(String url) {
    this.url = new File("")
        .getAbsolutePath()
        .concat(url);
  }

  public Path getPath() {
    return Paths.get(url);
  }

  public String getFileType() {
    try {
      return probeContentType(getPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String toString() {
    System.out.println(url);
    try {
      byte[] encoded = Files.readAllBytes(getPath());
      return new String(encoded, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    return null;
  }
}
