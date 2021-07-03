package com.sales.last.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sales.last.model.Tax;

@Repository
public interface TaxesRepository extends JpaRepository<Tax, Integer> {
	
	@Query( "select sum( t.rate ) from Tax t where t.type in :ids" )
	Double findByTypeIn(@Param("ids") List<String> type);
	
}
