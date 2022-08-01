/*
 *	Name Class: ContractTypeRepository 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Interface for the methods 
 * 				 used in the class ContractTypeRepository.
 */
package com.methaphorce_challenge.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.methaphorce_challenge.beansDB.ContractTypeBeanDb;

@EnableJpaRepositories
@Transactional
public interface ContractTypeRepository 
							extends JpaRepository<ContractTypeBeanDb, Short>{
	
	@Query("SELECT u FROM contracttype u WHERE contract_type_pk = ?1")
	public ContractTypeBeanDb getInfoContTypeById(Short contractId);
	
}
