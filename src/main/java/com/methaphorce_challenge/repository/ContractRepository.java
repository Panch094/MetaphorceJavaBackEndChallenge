/*
 *	Name Class: ContractRepository 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Interface for the methods used in the class ContractRepository.
 */
package com.methaphorce_challenge.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.methaphorce_challenge.beansDB.ContractBeanDb;

@EnableJpaRepositories
@Transactional
public interface ContractRepository extends JpaRepository<ContractBeanDb, Short>{
	
	@Query("SELECT u FROM contract u WHERE u_contract_pk = ?1")
	public ContractBeanDb getInfoContrById(Short contractId);
	
	@Query("SELECT u FROM contract u WHERE u_employee_fk = ?1")
	public ContractBeanDb getInfoContrByEmployee(Short employeeId);
	
}
