/*
 *	Name Class: ContractGetInfoBean 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Bean to get the information from the contract table.
 */
package com.methaphorce_challenge.beansJson;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractGetInfoBean {

	private Short contract_id;			//Primary Key auto-incremental id
	private Short employee_id;			//Id employee and foreign key
	private Integer contract_type; 		//Contract type foreign key
	private Timestamp date_from;		//Date from contract
	private Timestamp date_to;			//Date to contract
	private BigDecimal salary_per_day;	//Salary per day of employee
	private Boolean is_active;			//Is active the employee?
	private Timestamp date_created;		//Date of creation of the registry
	
}
