package com.ecorz.stressapp.stresstestagent.prometheus;

import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromQueryFields;
import java.util.HashMap;
import java.util.Map;

public class QueryStringGenerator {
  private final static String startDate = "start_time";
  private final static String endDate = "end_time";
  private final static String timeStep = "t_step";
  private final static String nodeNmbr = "node_nmbr";
  private final static String nodeId = "id";

  private QueryStringGenerator() {
  }

  public static String generatePostQueryNode18(PromQueryFields queryFields) {
    Map<String,String> vals = buildNodeAgnosticMap(queryFields);
    vals.put(nodeNmbr, "18");
    vals.put(nodeId, queryFields.getNode18Id());

    return VarSubstituter.substitute(vals, queryFields.getEncQueryTemplate());
  }

  public static String generatePostQueryNode20(PromQueryFields queryFields) {
    Map<String,String> vals = buildNodeAgnosticMap(queryFields);
    vals.put(nodeNmbr, "20");
    vals.put(nodeId, queryFields.getNode20Id());

    return VarSubstituter.substitute(vals, queryFields.getEncQueryTemplate());
  }

  private static Map<String,String> buildNodeAgnosticMap(PromQueryFields queryFields) {
    Map<String,String> vals = new HashMap<>();
    vals.put(startDate, queryFields.getStartDate());
    vals.put(endDate, queryFields.getEndDate());
    vals.put(timeStep, queryFields.getTimeStep());

    return vals;
  }
}
