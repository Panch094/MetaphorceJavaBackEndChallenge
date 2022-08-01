/*
 *	Name Class: ContractBeanDb 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Bean to parse the database information.
 */
package com.methaphorce_challenge.beansDB;

import java.beans.JavaBean;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JavaBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contract")
public class ContractBeanDb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short u_contract_pk;		//Primary Key auto-incremental id
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "employee.emp_employee_pk")
	private Short u_employee_fk;		//Id employee and foreign key
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "contracttype.contract_type_pk")
	private Integer u_contract_type_fk; //Contract type foreign key
	private Timestamp u_date_from;		//Date from contract
	private Timestamp u_date_to;		//Date to contract
	private BigDecimal u_salary_per_day;//Salary per day of employee
	private Boolean u_is_active;		//Is active the employee?
	private Timestamp u_date_created;	//Date of creation of the registry
	
}
