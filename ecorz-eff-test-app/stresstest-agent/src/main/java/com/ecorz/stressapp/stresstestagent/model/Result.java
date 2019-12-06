package com.ecorz.stressapp.stresstestagent.model;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {
  @Id
  @Column(name = "result_id", nullable = false)
  private String resultId;

  @Column(name = "result_dump_file", nullable = false)
  private String resultdumpFile;

  public void setResultId(String resultId) {
    this.resultId = resultId;
  }

  public String getResultId() {
    return resultId;
  }

  public void setResultdumpFile(String resultdumpFile) {
    this.resultdumpFile = resultdumpFile;
  }

  public String getResultdumpFile() {
    return resultdumpFile;
  }
}
