/*
 *	Name Class: ContractController 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Class where the five endpoints 
 * 				 required in the challenge are created.
 */
package com.methaphorce_challenge.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.methaphorce_challenge.beansJson.EmployeeActiveResponse;
import com.methaphorce_challenge.beansJson.EmployeeActiveResponse.EmployeeActiveResponseBuilder;
import com.methaphorce_challenge.process.ContractToolsProcess;

@RestController
public class ContractController extends ContractToolsProcess{	
	
	/**
	 *	<h1> Endpoint of employee information </h1>
	 *	<p>
	 *		 
	 *	</p>
	 *	@param
	 *	@apiNote
	 *	@return
	 */
	@GetMapping(
			value = "active_employee", 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<EmployeeActiveResponse> getActiveEmployee(
			@RequestParam Long contract_id,				//Contract id
			@RequestHeader(
					name = HttpHeaders.AUTHORIZATION, 
					required = true
			) String authorizer							//Auth is a basic64
	){
		
		HttpStatus httpService = HttpStatus.NOT_ACCEPTABLE;
		Integer employeeId = getEmployeeId(contract_id);
				
		
		/*Verify if the credentials are correct.*/
		if(!credentialVerificationOtherValildations(authorizer, contract_id)) {
			httpService = HttpStatus.UNAUTHORIZED;
			return new ResponseEntity<EmployeeActiveResponse>(
				buildSpecificResponse(contract_id, "no-auth"), 
				httpService
			);
		}
		
		/*Verification of active contract.*/
		if(!validationOfInfo(contract_id)) {
			httpService = HttpStatus.UNAUTHORIZED;
			return new ResponseEntity<EmployeeActiveResponse>(
				buildSpecificResponse(contract_id, "null"), 
				httpService
			);
		}		
		
		EmployeeActiveResponseBuilder serviceR = EmployeeActiveResponse
				.builder()
				.fullName(employeeFullName(contract_id))
				.taxId(getInfoEmployee(employeeId).getEmp_tax_id_number())
				.email(getInfoEmployee(employeeId).getEmp_email())
				.typeContract(getInfoTypeContract(
								integerToShort(employeeId)).getContract_type_pk()
							 )
				.contsDate(getInfoContract(
								longToShort(contract_id)).getU_date_from()
						  )
				.contfDate(getInfoContract(
								longToShort(contract_id)).getU_date_to()
						  )
				.salaryDay(getInfoContract(
								longToShort(contract_id)).getU_salary_per_day()
						  )
				.response(buildGenericResponse(
						contract_id,
						"Active employee information > " + employeeFullName(contract_id), 
						true)
				);
		
		return new ResponseEntity<EmployeeActiveResponse>(serviceR.build(),httpService);
	}	
}
