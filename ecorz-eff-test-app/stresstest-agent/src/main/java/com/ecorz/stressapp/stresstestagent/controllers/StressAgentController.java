package com.ecorz.stressapp.stresstestagent.controllers;

import com.ecorz.stressapp.stresstestagent.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.services.ResultService;
import com.ecorz.stressapp.stresstestagent.services.RunService;
import com.ecorz.stressapp.stresstestagent.model.Run;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

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

    @RequestMapping(value="/run/setrun",method = RequestMethod.POST)
    public UUID setRun( @Valid @RequestBody RunConfigFields configFields) {
      return runService.saveRun(configFields);
    }

    @RequestMapping(value="/run",method = RequestMethod.GET)
    public List<RunConfigFields> getRunConfigFieldsList() {
      return runService.getRuns();
    }

    @RequestMapping(value="/run/{runId}",method = RequestMethod.POST)
    public UUID startRun( @PathVariable("runId") String runId) {
      UUID runUuid = UUID.fromString(runId);
      try {
        return runService.startRun(runUuid);
      } catch (RunException e) {
        LOGGER.error(String.format("Cannot execute the run with id: %s", runId));
      }

      return null;
    }

    @RequestMapping(value="/result",method = RequestMethod.GET)
    public List<ResultDomain> getResults() {
      return resultService.getResults();
    }
}
