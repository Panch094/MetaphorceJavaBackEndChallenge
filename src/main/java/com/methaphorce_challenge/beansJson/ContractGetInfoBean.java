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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"EmmployeeId",
		"ContractType", "DateFrom", "DateTo", "SalaryPerDay",
		"IsActive", "DateCreated"
	    })
public class ContractGetInfoBean {
	
	@JsonProperty("EmmployeeId") 
	private Short employee_id;			//Id employee and foreign key
	
	@JsonProperty("ContractType") 
	private Integer contract_type; 		//Contract type foreign key

	@JsonProperty("DateFrom") 
	private Timestamp date_from;		//Date from contract
	
	@JsonProperty("DateTo") 
	private Timestamp date_to;			//Date to contract
	
	@JsonProperty("SalaryPerDay") 
	private BigDecimal salary_per_day;	//Salary per day of employee
	
	@JsonProperty("IsActive") 
	private Boolean is_active;			//Is active the employee?
	
	@JsonProperty("DateCreated") 
	private Timestamp date_created;		//Date of creation of the registry
	
}
