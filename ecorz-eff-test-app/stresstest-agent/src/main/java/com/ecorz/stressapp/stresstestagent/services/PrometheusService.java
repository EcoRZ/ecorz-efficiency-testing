package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.config.PrometheusServiceConfig;
import com.ecorz.stressapp.stresstestagent.prometheus.PrometheusException;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryFields;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryFields.Builder;
import com.ecorz.stressapp.stresstestagent.prometheus.TimeGenerator;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {
  @Autowired
  private PrometheusServiceConfig prometheusServiceConfig;

  public QueryFields generateFields(long startTime, long endTime) {
    int startOffMs = Integer.valueOf(prometheusServiceConfig.getPromStartOff());
    int endOffMs = Integer.valueOf(prometheusServiceConfig.getPromEndOff());

    String startDate = TimeGenerator.generateRfc3339TimeMinusOff(startTime, startOffMs);
    String endDate = TimeGenerator.generateRfc3339TimePlusOff(endTime, endOffMs);

    QueryFields fields = Builder.getBuilder().startDate(startDate).
        endDate(endDate).promUser(prometheusServiceConfig.getPromUser()).
        promPassword(prometheusServiceConfig.getPromPassword()).
        promRestEnd(prometheusServiceConfig.getPromRestEnd()).
        node19Cpus(prometheusServiceConfig.getPromNode19Cpus()).
        node20Cpus(prometheusServiceConfig.getPromNode20Cpus()).
        build();
    return fields;
  }


  public String generateDumpFileName() {
    // todo: implement approprietly
    return "dumpFile";
  }

  public void dump(QueryFields fields, ResultFile dumpFile) throws PrometheusException {
    //todo: implement like:
    // curl -u ${USER}:${PASSWORD} "http://$prom_rest_api_end/$query_cmd_ident=$query_cmd" | sed -e 's/.*"values":\[\(.*]\)\].*/\1/g' | sed -e 's/\],\[/]\n[/g' | sed -e 's/\[\(.*\),"\(.*\)"\]/\1,\2/g' | awk 'BEGIN { printf "%-10s %s\n", "Time[s]", "Avg_cpus_load" } {}1' # > "$filename"
  }
}
