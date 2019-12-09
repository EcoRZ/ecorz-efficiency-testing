package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

  // todo: Use Repos and consequently Opstgres DB
  // @Autowired
  // private ResultRepository resultRepository;
  @Autowired
  private TmpRepository tmpRepository;

  public UUID saveResult(ResultPersist resultPersist) {
    UUID uuid = UUID.randomUUID();
    tmpRepository.addResultPersist(uuid, resultPersist);

    return uuid;
  }

  public List<ResultPersist> getResults() {
    return new ArrayList<ResultPersist>(tmpRepository.getResultPersistMap().values());
  }
}
