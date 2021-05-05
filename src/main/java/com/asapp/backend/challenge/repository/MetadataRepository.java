package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.resources.MetadataResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends CrudRepository<MetadataResource, Long> {

}
