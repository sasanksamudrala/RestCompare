package com.scalable.diff.restcompare.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;

/**
 * Interface to intermediate the operations to be performed on repository
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public interface JsonBinaryDataRepository extends CrudRepository<JsonBinaryDataEntity, Long> {

	/**
     * Find All the records for a Given InputId and Direction.
     * 
     * @param inputId the input ID from the user
     * @param Direction indicates whether the input is for Left or Right Window
     * 
     */
    List<JsonBinaryDataEntity> findByInputIdAndDirection(Integer inputId, String direction);
    
    /**
     * Delete All the records for a Given InputId and Direction.
     * 
     * @param inputId the input ID from the user
     * @param Direction indicates whether the input is for Left or Right Window
     * 
     */
    List<JsonBinaryDataEntity> deleteByInputIdAndDirection(Integer inputId, String direction);
    
    
}
