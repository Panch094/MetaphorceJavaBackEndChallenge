/*
 *	Name Class: ContractController 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Class where the five endpoints 
 * 				 required in the challenge are created.
 */
package com.methaphorce_challenge.controller;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.methaphorce_challenge.beansDB.ContractBeanDb;
import com.methaphorce_challenge.beansDB.EmployeeBeanDb;
import com.methaphorce_challenge.beansJson.AddNewContractResponse;
import com.methaphorce_challenge.beansJson.AddNewContractResponse.AddNewContractResponseBuilder;
import com.methaphorce_challenge.beansJson.AddNewEmployeeResponse;
import com.methaphorce_challenge.beansJson.AddNewEmployeeResponse.AddNewEmployeeResponseBuilder;
import com.methaphorce_challenge.beansJson.ContractGetInfoBean;
import com.methaphorce_challenge.beansJson.EmployeeActiveResponse;
import com.methaphorce_challenge.beansJson.EmployeeActiveResponse.EmployeeActiveResponseBuilder;
import com.methaphorce_challenge.beansJson.EmployeeGetInfoBean;
import com.methaphorce_challenge.process.ContractToolsProcess;

@RestController
public class ContractController extends ContractToolsProcess{	
	
	/**
	 *	<h1> Employee Active </h1>
	 *	<p>
	 *		Service that returns all active and contracted employees.
	 *	</p>
	 *
	 *	@RequestHeader authorizer
	 *	@return new ResponseEntity<EmployeeActiveResponse>
	 */
	@GetMapping(
			value = "active_employee", 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<EmployeeActiveResponse>> getActiveEmployee(
			@RequestHeader(
					name = HttpHeaders.AUTHORIZATION, 
					required = true
			) String authorizer							//Auth is a basic64
	){
		
		ArrayList<EmployeeBeanDb> listEmployees = new ArrayList<>();
		ArrayList<EmployeeActiveResponse> responseR = new ArrayList<>();
		HttpStatus httpService = HttpStatus.NOT_ACCEPTABLE;
		String fullName = null;
		Long contractId = null;
				
		
		/*Verify if the credentials are correct.*/
		if(!credentialVerificationOtherValildations(authorizer)) {
			httpService = HttpStatus.UNAUTHORIZED;
			return new ResponseEntity<ArrayList<EmployeeActiveResponse>>(
					responseR = null,
					httpService
			);
		}
		
		/*Get list of active employes*/
		listEmployees = findListOfEmpActive();
		
		for(int i=0; i<listEmployees.size(); i++) {
			
			if(checkEmployeeContract(listEmployees.get(i).getEmp_employee_pk())){
				
				fullName = "";
				fullName +=
					listEmployees.get(i).getEmp_name() 
					+ " " + 
					listEmployees.get(i).getEmp_last_name();
					
				contractId = shortToLong(getContractId(
									listEmployees.get(i).getEmp_employee_pk()));
					
				EmployeeActiveResponseBuilder serviceR = EmployeeActiveResponse
					.builder()
					.fullName(fullName.toString())
					.taxId(listEmployees.get(i).getEmp_tax_id_number())
					.email(listEmployees.get(i).getEmp_email())
					.typeContract(integerToShort(getInfoContract(
								longToShort(contractId)).getU_contract_type_fk()))
					.contsDate(getInfoContract(
									longToShort(contractId)).getU_date_from()
							  )
					.contfDate(getInfoContract(
									longToShort(contractId)).getU_date_to()
							  )
					.salaryDay(getInfoContract(
									longToShort(contractId)).getU_salary_per_day()
							  )
					.response(buildGenericResponse(
						contractId,
						"Active employee information > " + fullName.toString(), 
						true)
					);
				
					responseR.add(serviceR.build());
			}else {
				fullName = "";
				fullName +=
					listEmployees.get(i).getEmp_name() 
					+ " " + 
					listEmployees.get(i).getEmp_last_name();
				
				EmployeeActiveResponseBuilder serviceR = EmployeeActiveResponse
					.builder()
					.fullName(fullName.toString())
					.taxId(null)
					.email(null)
					.typeContract(null)
					.contsDate(null)
					.contfDate(null)
					.salaryDay(null)
					.response(buildGenericResponse(
						null,
						"Active employee information > " + fullName.toString(), 
						true)
					);
				
					responseR.add(serviceR.build());
			}
			
		}
		
		return new ResponseEntity<ArrayList<EmployeeActiveResponse>>(
				responseR,
				httpService
		);
		
	}// End of function getActiveEmployee

	/**
	 *	<h1> Add new contract </h1>
	 *	<p>
	 *		End point to create or edit a contract per employee.
	 *	</p>
	 *
	 *	@RequestParam employee_id
	 *	@RequestHeader authorizer
	 *	@RequestBody contractToAdd
	 *	@return new ResponseEntity<AddNewContractResponse>
	 */
	@PostMapping(
			value = "add_contract", 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<AddNewContractResponse> addNewContract(
			@RequestParam Integer employee_id,			//Employee id
			@RequestHeader(
					name = HttpHeaders.AUTHORIZATION, 
					required = true
			) String authorizer,						//Auth is a basic64
			@RequestBody ContractGetInfoBean contractToAdd
	){		
		AddNewContractResponseBuilder responseB = AddNewContractResponse
				.builder();
		HttpStatus httpService = HttpStatus.NOT_ACCEPTABLE;
		ContractBeanDb responseNewCont = new ContractBeanDb();
		Short contractId = 0;
		StringBuffer action = new StringBuffer();		//Variable indicating 
														//endpoint action.
		
		/*Set employeed id*/
		contractToAdd.setEmployee_id(integerToShort(employee_id));
		
		/*Verify if the credentials are correct.*/
		if(!credentialVerificationOtherValildations(authorizer)) {
			httpService = HttpStatus.UNAUTHORIZED;
			return new ResponseEntity<AddNewContractResponse>(
				buildSpecificResponseAdded(employee_id.toString(), "no-auth"), 
				httpService
			);
		}
		
		/*Check that contract does not exist*/
		if(!checkEmployeeContract(employee_id)) {
			/*Create a new contract*/
			responseNewCont = addNewContract(contractToAdd);
			contractId = responseNewCont.getU_contract_pk();
			action.append("CREATED");
			httpService = HttpStatus.CREATED;
			
		}else if(checkEmployeeContract(employee_id)){
			/*Exchange of old contract for new*/
			contractId = getContractId(employee_id);
			contractRepo.deleteById(contractId);
			responseNewCont = addNewContract(contractToAdd);
			action.append("REPLACED");
			httpService = HttpStatus.CREATED;
		}
		
		responseB
			.identifier(contractId.toString())
			.contractAction(action.toString())
			.contractAdded(contractToAdd);
		
		return new ResponseEntity<AddNewContractResponse>(
				responseB.build(), 
				httpService
		);
	}// End of function addNewContract
	
	/**
	 *	<h1> Add new employee </h1>
	 *	<p>
	 *	
	 *	</p>
	 *
	 *	@RequestParam employee_id
	 *	@RequestHeader authorizer
	 *	@RequestBody contractToAdd
	 *	@return new ResponseEntity<AddNewContractResponse>
	 */
	@PostMapping(value = "add_employee", 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<AddNewEmployeeResponse> addNewEmployee(
			@RequestBody EmployeeGetInfoBean addToEmployee,
			@RequestHeader(
					name = HttpHeaders.AUTHORIZATION, 
					required = true
			) String authorizer
	) {
		AddNewEmployeeResponseBuilder responseB = AddNewEmployeeResponse
				.builder();
		HttpStatus httpService = HttpStatus.NOT_ACCEPTABLE;
		String taxIdNum = addToEmployee.getTax_id_number();
		String employeeName = addToEmployee.getName();
		StringBuffer action = new StringBuffer();
		
		/*Verify if the credentials are correct.*/
		if(!credentialVerificationOtherValildations(authorizer)) {
			httpService = HttpStatus.UNAUTHORIZED;
			return new ResponseEntity<AddNewEmployeeResponse>(
				buildSpecificResponseEmp(employeeName, "no-auth"), 
				httpService
			);
		}
		
		if(validatorRFC(taxIdNum) && !checkTaxIdNumExist(taxIdNum)) {
			
			addNewEmployee(addToEmployee);
			httpService = HttpStatus.CREATED;
			action.append("CREATED");
			
		}else {
			httpService = HttpStatus.BAD_REQUEST;
			action.append("NO CREATED");
			return new ResponseEntity<AddNewEmployeeResponse>(
					buildSpecificResponseEmp(employeeName, "null"), 
					httpService
				);
		}
		
		responseB
			.identifier(employeeName)
			.employeeAction(action.toString())
			.employeeAdded(addToEmployee);
		
		return new ResponseEntity<AddNewEmployeeResponse>(
				responseB.build(), 
				httpService
		);		
	}
	
}
