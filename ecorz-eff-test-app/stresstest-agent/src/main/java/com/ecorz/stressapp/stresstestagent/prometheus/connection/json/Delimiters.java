package com.ecorz.stressapp.stresstestagent.prometheus.connection.json;

public class Delimiters {

  public final String startDel;
  public final String endDel;
  public final String consecStrPattern;

  public Delimiters(DelType type, String consecStrPattern) {
    if (type == DelType.EDGE) {
      this.startDel = "\\[";
      this.endDel = "\\]";
    } else {
      this.startDel = this.endDel = "\"";
    }

    this.consecStrPattern = consecStrPattern;
  }

  public enum DelType { EDGE, QUOTE };
}
