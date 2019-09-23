package io.pivotal.repositories;

import java.util.List;

import org.springframework.data.gemfire.repository.GemfireRepository;

import org.springframework.stereotype.Repository;

import io.pivotal.bookshop.domain.BookMaster;

@Repository
public interface BookMasterRepository extends GemfireRepository<BookMaster, Integer>{

	List<BookMaster> findByItemNumber(Integer itemNumber);
	
}
