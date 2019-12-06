package com.ecorz.stressapp.stresstestagent.repository;

import com.ecorz.stressapp.stresstestagent.model.Run;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends CrudRepository<Run,String> {

}
