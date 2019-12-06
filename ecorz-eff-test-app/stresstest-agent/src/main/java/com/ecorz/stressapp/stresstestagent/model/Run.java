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
@Table(name = "runs")
public class Run {
  @Id
  @Column(name = "run_id", nullable = false)
  private String runId;

  @Column(name = "benchmark_name", nullable = false)
  private String benchmarkName;

  @ElementCollection
  @MapKeyColumn(name = "bmParametersName")
  @Column(name = "bmParametersValue")
  private Map<String, String> bmParameters;

  public void setRunId(String runId) {
    this.runId = runId;
  }

  public String getRunId() {
    return runId;
  }

  public void setBenchmarkName(String benchmarkName) {
    this.benchmarkName = benchmarkName;
  }

  public String getBenchmarkName() {
    return benchmarkName;
  }

  public Map<String, String> getBmParameters() {
    return ImmutableMap.copyOf(bmParameters);
  }

  public void addBmParameter(String parName, String parValue) {
    if(parName == null) {
      bmParameters = new HashMap<>();
    }
    this.bmParameters.put(parName, parValue);
  }
}
