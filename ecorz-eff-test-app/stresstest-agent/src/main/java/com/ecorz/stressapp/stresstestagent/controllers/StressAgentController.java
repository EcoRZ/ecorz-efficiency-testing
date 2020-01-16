package com.ecorz.stressapp.stresstestagent.controllers;

import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomainResponse;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFieldsResponse;
import com.ecorz.stressapp.stresstestagent.services.ResultService;
import com.ecorz.stressapp.stresstestagent.services.RunService;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import com.ecorz.stressapp.common.run.RunException;

/*
  Was brauch ich:
  - POST Methode setrun: zum Setzen des Simulations-BenchmarkContainer: Benchmark, Parameter. Wird final in db persistiert, vorerst als var speichern
  - GET Methode getruns: Querien aller gespeicherten runs. Wird final aus db gelesen, vorerst aus var lesen
  - POST Methode startrun/id: zum Starten des Simulations-Runs
  - GET Methode getresults: Querien aller gespeicherten run-resultate (Verweis auf Dateien). Wird final aus db gelesen, vorerst aus var lesen
 */
@RestController
@RequestMapping(value="v1")
public class StressAgentController {
    @Autowired
    private RunService runService;
    @Autowired
    private ResultService resultService;
    private final static Logger LOGGER = LoggerFactory.getLogger(StressAgentController.class);

    @RequestMapping(value="/run/setconfig",method = RequestMethod.POST)
    public UUID setRun( @Valid @RequestBody RunConfigFields configFields) {
      return runService.saveRun(configFields);
    }

    @RequestMapping(value="/run/setfile",method = RequestMethod.POST)
    public UUID setRun( @Valid @RequestBody String file) {
      return runService.saveRun(file);
    }

    @RequestMapping(value="/run",method = RequestMethod.GET)
    public List<RunConfigFieldsResponse> getRunConfigFieldsList() {
      return runService.getRuns();
    }

    @RequestMapping(value="/run/{runId}",method = RequestMethod.POST)
    public UUID startRun( @PathVariable("runId") String runId) {
      UUID runUuid = UUID.fromString(runId);
      try {
        return runService.startRun(runUuid);
      } catch (RunException e) {
        LOGGER.error(String.format("Cannot execute the com.ecorz.stressapp.common.run with id: %s", runId));
      }

      return null;
    }

    @RequestMapping(value="/run/{runId}",method = RequestMethod.DELETE)
    public void deleteRun( @PathVariable("runId") String runId) {
      UUID uuid = UUID.fromString(runId);
      runService.deleteRun(uuid);
    }

    @RequestMapping(value="/result",method = RequestMethod.GET)
    public List<ResultDomainResponse> getResults() {
      return resultService.getResults();
    }
}
