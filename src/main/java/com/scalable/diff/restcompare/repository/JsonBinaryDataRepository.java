package com.scalable.diff.restcompare.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;

public interface JsonBinaryDataRepository extends CrudRepository<JsonBinaryDataEntity, Long> {

	List<JsonBinaryDataEntity> findByInputIdAndDirection(Integer inputId, String direction);
	
	List<JsonBinaryDataEntity> deleteByInputIdAndDirection(Integer inputId, String direction);
	
	
}
