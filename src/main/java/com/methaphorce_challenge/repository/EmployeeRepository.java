/*
 *	Name Class: EmployeeRepository 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Interface for the methods used in the class EmployeeRepository.
 */
package com.methaphorce_challenge.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.methaphorce_challenge.beansDB.ContractTypeBeanDb;
import com.methaphorce_challenge.beansDB.EmployeeBeanDb;

@EnableJpaRepositories
@Transactional
public interface EmployeeRepository 
								extends JpaRepository<EmployeeBeanDb, Integer>{

	@Query("SELECT u FROM employee u WHERE emp_employee_pk = ?1")
	public EmployeeBeanDb getInfoEmployeeById(Integer contractId);
	
}
