/*
 *	Name Class: EmployeeRepository 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Interface for the methods used in the class EmployeeRepository.
 */
package com.methaphorce_challenge.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.methaphorce_challenge.beansDB.EmployeeBeanDb;

@EnableJpaRepositories
@Transactional
public interface EmployeeRepository 
								extends JpaRepository<EmployeeBeanDb, Integer>{

	@Query(nativeQuery = true, 
			value = "SELECT * FROM employee WHERE emp_employee_pk = ?1")
	public EmployeeBeanDb getInfoEmployeeById(Integer contractId);
	
	@Query(nativeQuery = true, 
			value = "SELECT * FROM employee WHERE emp_tax_id_number = ?1")
	public EmployeeBeanDb getInfoByTaxIdNum(String taxIdNum);
	
	@Query(nativeQuery = true, 
			value = "SELECT * FROM employee WHERE emp_is_active = 1")
	public ArrayList<EmployeeBeanDb> getListOfActiveEmp();
	
}
