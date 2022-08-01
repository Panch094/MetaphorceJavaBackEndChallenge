/*
 *	Name Class: EmployeeActiveResponse 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Response bean for the first endpoint of the challenge.
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
@JsonPropertyOrder(value = {
							"FullName", "TaxIdNumber", "Email", "TypeCont",
							"ContStrDate", "ContEndDate", "SalaryDay", 
							"ResponseWeb"
							})
public class EmployeeActiveResponse {

	@JsonProperty("FullName") private String fullName;		//Employee full name
	@JsonProperty("TaxIdNumber") private String taxId;		//Employee RFC
	@JsonProperty("Email") private String email;			//Employee email
	@JsonProperty("TypeCont") private Short typeContract;	//Name contract type
	@JsonProperty("ContStrDate") private Timestamp contsDate;//Start date contract
	@JsonProperty("ContEndDate") private Timestamp contfDate;//Final date contract
	@JsonProperty("SalaryDay") private BigDecimal salaryDay;//Employee salary day
	
	/*General service response*/
	@JsonProperty("ResponseWeb") private GenericServiceResponse response;
	
}
