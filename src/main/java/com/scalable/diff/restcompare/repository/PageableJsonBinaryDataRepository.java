package com.scalable.diff.restcompare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;

/**
 * For Paging the results so that the Client can display only few required records
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

public interface PageableJsonBinaryDataRepository extends PagingAndSortingRepository<JsonBinaryDataEntity, Long>  {

	/**
     * Find All the records for a Given InputId.
     * 
     * @param inputId the input ID from the user
     * 
     */
    Page<JsonBinaryDataEntity> findByInputId(Integer inputId, Pageable page);
    
}
