package com.mutant.domain;
import org.springframework.data.annotation.Id;

public class Greeting {

  @Id
  private String id;
  private final long num;
  private final String content;

  public Greeting(long num, String content) {
    this.num = num;
    this.content = content;
  }

  public long getId() {
    return num;
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "Greeting{" +
            "id='" + id + '\'' +
            ", num=" + num +
            ", content='" + content + '\'' +
            '}';
  }
}
