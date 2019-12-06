package com.ecorz.stressapp.stresstestagent.repository;

import com.ecorz.stressapp.stresstestagent.model.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result,String>  {
}
