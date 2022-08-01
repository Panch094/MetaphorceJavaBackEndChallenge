/*
 *	Name Class: ContractToolsProcess 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Class of the tools necessary to read data in the DB.
 */
package com.methaphorce_challenge.process;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.methaphorce_challenge.beansDB.ContractBeanDb;
import com.methaphorce_challenge.beansDB.ContractBeanDb.ContractBeanDbBuilder;
import com.methaphorce_challenge.beansDB.ContractTypeBeanDb;
import com.methaphorce_challenge.beansDB.EmployeeBeanDb;
import com.methaphorce_challenge.beansDB.EmployeeBeanDb.EmployeeBeanDbBuilder;
import com.methaphorce_challenge.beansJson.AddNewContractResponse;
import com.methaphorce_challenge.beansJson.AddNewContractResponse.AddNewContractResponseBuilder;
import com.methaphorce_challenge.beansJson.AddNewEmployeeResponse;
import com.methaphorce_challenge.beansJson.AddNewEmployeeResponse.AddNewEmployeeResponseBuilder;
import com.methaphorce_challenge.beansJson.ContractGetInfoBean;
import com.methaphorce_challenge.beansJson.ContractGetInfoBean.ContractGetInfoBeanBuilder;
import com.methaphorce_challenge.beansJson.EmployeeActiveResponse;
import com.methaphorce_challenge
				.beansJson
				.EmployeeActiveResponse
				.EmployeeActiveResponseBuilder;
import com.methaphorce_challenge.beansJson.EmployeeGetInfoBean;
import com.methaphorce_challenge.beansJson.EmployeeGetInfoBean.EmployeeGetInfoBeanBuilder;
import com.methaphorce_challenge.beansJson.GenericServiceResponse;
import com.methaphorce_challenge
				.beansJson
				.GenericServiceResponse
				.GenericServiceResponseBuilder;
import com.methaphorce_challenge.repository.ContractRepository;
import com.methaphorce_challenge.repository.ContractTypeRepository;
import com.methaphorce_challenge.repository.EmployeeRepository;

import lombok.Data;

@Data
@Service
public class ContractToolsProcess{

	@Autowired
	protected ContractRepository contractRepo;
	
	@Autowired
	protected ContractTypeRepository contractTypeRepo;
	
	@Autowired
	protected EmployeeRepository employeeRepo;
	
	/*Conversion from Long to Short by String*/
	public Short longToShort(Long num) {
		return Short.parseShort(num.toString());
	}
	
	/*Conversion from Short to Integer by String*/
	public Integer shortToInteger(Short num) {
		return Integer.valueOf(num.toString());
	}
	
	/*Conversion from Integer to Short by String*/
	public Short integerToShort(Integer num) {
		return Short.parseShort(num.toString());
	}
	
	/*Conversion from Short to Long by String*/
	public Long shortToLong(Short num) {
		return Long.parseLong(num.toString());
	}
	
	/*Gets contract information.*/
	public ContractBeanDb getInfoContract(Short id_contract) {
		
		ContractBeanDb response = new ContractBeanDb();
		
		try {
			response = contractRepo.getReferenceById(id_contract);
		}catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Wrong contract id"
			);
		}
		
		return response;	
	}
	
	/*Gets contract type information.*/
	public ContractTypeBeanDb getInfoTypeContract(Short contractId) {
		
		ContractTypeBeanDb response = new ContractTypeBeanDb();
		
		try {
			response = contractTypeRepo.getReferenceById(contractId);
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Wrong contract id"
			);
		}
		
		return response;
	}
	
	/*Gets employee information.*/
	public EmployeeBeanDb getInfoEmployee(Integer employeeId) {
		
		EmployeeBeanDb response = new EmployeeBeanDb();
		
		try {
			response = employeeRepo.getInfoEmployeeById(employeeId);
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Wrong contract id"
			);
		}
		
		return response;		
	}
	
	/*Review of credentials*/
	public Boolean credentialVerificationOtherValildations(String authorizer) {
		
		String[] tokenAuth = authorizer.split(" "), convinationUp;
		Base64.Decoder decoder = Base64.getDecoder();
		String getByteAbove;
		
		if(tokenAuth[0].equalsIgnoreCase("Basic")){
			
			byte[] byteArrayDecoder = decoder.decode(tokenAuth[1]);
			getByteAbove = new String(byteArrayDecoder);
			convinationUp = getByteAbove.split(":");
			
			if(convinationUp[0].equalsIgnoreCase("metaphorce")) {
				if(convinationUp[1].equalsIgnoreCase("m3t4Ph0rc3")){
					return true;
				}else {
					return false;
				}
			}else {
				return false;				
			}
		}
		return null;
	}
	
	/*Validation of information.*/
	public Boolean validationOfInfo(Long contractId) {
		
		ContractBeanDb responseRepo = new ContractBeanDb();
		
		responseRepo = contractRepo.getReferenceById(longToShort(contractId));
		
		/*Date start employee validation*/
		if(responseRepo.getU_date_from() != null) {
			/*Date final employee validation*/
			if(responseRepo.getU_date_to() != null) {
				/*Active employee validation*/
				if(responseRepo.getU_is_active()) {
					return true;
				}else if(!responseRepo.getU_is_active()) {
					return false;
				}
			}else {
				return false;
			}
		}else {
			return false;
		}
		
		return null;
	}
	
	/*Build generic message to active employee.*/
	public EmployeeActiveResponse buildSpecificResponseActive(
			Long contractId, String typeR		
	) {
		
		GenericServiceResponseBuilder gServiceR = GenericServiceResponse
				.builder();
		EmployeeActiveResponseBuilder builderResponse = EmployeeActiveResponse
				.builder();
		
		switch(typeR) {
		
			case "no-auth":
				gServiceR.contract_id(contractId)
				.message("Wrong credentials")
				.responseStatus(false);
				
				builderResponse
				.response(gServiceR.build())
				.fullName(null)
				.taxId(null)
				.email(null)
				.typeContract(null)
				.contsDate(null)
				.contfDate(null)
				.salaryDay(null);
			break;
			
			case "null":
				
				gServiceR
				.contract_id(contractId)
				.message("Bad information")
				.responseStatus(false);
				
				builderResponse
				.response(gServiceR.build())
				.fullName(null)
				.taxId(null)
				.email(null)
				.typeContract(null)
				.contsDate(null)
				.contfDate(null)
				.salaryDay(null);
				
			break;
			
		}
		
		return builderResponse.build();		
	}
	
	/*Build generic message to add contract.*/
	public AddNewContractResponse buildSpecificResponseAdded(
			String identifier, String typeR		
	) {
		
		ContractGetInfoBeanBuilder contractInfo = ContractGetInfoBean
				.builder();
		AddNewContractResponseBuilder builderResponse = AddNewContractResponse	
				.builder();
		
		switch(typeR) {
		
			case "no-auth":
				contractInfo
					.employee_id(null)
					.contract_type(null)
					.date_from(null)
					.date_to(null)
					.salary_per_day(null)
					.is_active(null)
					.date_created(null);
				
				builderResponse
					.identifier(identifier)
					.contractAction("Not reviewed")
					.contractAdded(contractInfo.build());
			break;
			
			case "null":
				
			contractInfo
				.employee_id(null)
				.contract_type(null)
				.date_from(null)
				.date_to(null)
				.salary_per_day(null)
				.is_active(null)
				.date_created(null);
			
			builderResponse
				.identifier(identifier)
				.contractAction("Not reviewed")
				.contractAdded(contractInfo.build());
				
			break;
			
		}
		
		return builderResponse.build();		
	}
	
	/*Build generic message to add employee.*/
	public AddNewEmployeeResponse buildSpecificResponseEmp(
			String identifier, String typeR		
	) {
		
		EmployeeGetInfoBeanBuilder employeeInfo = EmployeeGetInfoBean
				.builder();
		AddNewEmployeeResponseBuilder builderResponse = AddNewEmployeeResponse	
				.builder();
		
		switch(typeR) {
		
			case "no-auth":
				employeeInfo
					.tax_id_number(null)
					.name(null)
					.last_name(null)
					.birthdate(null)
					.email(null)
					.cellphone(null)
					.is_active(null)
					.date_created(null);
				
				builderResponse
					.identifier(identifier)
					.employeeAction("Not reviewed")
					.employeeAdded(employeeInfo.build());
			break;
			
			case "null":
				employeeInfo
					.tax_id_number(null)
					.name(null)
					.last_name(null)
					.birthdate(null)
					.email(null)
					.cellphone(null)
					.is_active(null)
					.date_created(null);
				
				builderResponse
					.identifier(identifier)
					.employeeAction("Not reviewed")
					.employeeAdded(employeeInfo.build());
			break;
			
		}
		
		return builderResponse.build();		
	}
	
	/*Build generic message.*/
	public GenericServiceResponse buildGenericResponse(
			Long contractId, 
			String ResponseMessage, 
			Boolean ResponseStauts
	) {
		
		GenericServiceResponseBuilder gServiceR = GenericServiceResponse
				.builder()
				.contract_id(contractId)
				.message(ResponseMessage)
				.responseStatus(ResponseStauts);
		
		return gServiceR.build();		
	}
	
	/*Gets employee id by contract id*/
	public Integer getEmployeeId(Long contractId) {
		
		ContractBeanDb responseDb = new ContractBeanDb();
		Short employeeId = null;
		
		try {
			responseDb = contractRepo.getInfoContrById(longToShort(contractId));
			employeeId = responseDb.getU_employee_fk();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"No contract id exists"
			);
		}
		return shortToInteger(employeeId);
	}
	
	/*Gets contract id by employee id*/
	public Short getContractId(Integer employeeId) {
		
		Short response = null;
		
		try {
			response = contractRepo.getInfoContrByEmployee(employeeId)
															.getU_contract_pk();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"No contract id exists"
			);
		}
		
		return response;
		
	}
	
	/*Gets employee full name*/
	public String employeeFullName(Long contractId) {
		
		return getInfoEmployee(getEmployeeId(contractId)).getEmp_name()
			   + " " + 
			   getInfoEmployee(getEmployeeId(contractId)).getEmp_last_name();
	}
	
	/*Find contract id by employee id*/
	public Boolean checkEmployeeContract(Integer employeeId) {
		
		ContractBeanDb responseDb = new ContractBeanDb();
		Short contractId = null;
		
		try {
			responseDb = contractRepo.getInfoContrByEmployee(employeeId);
			contractId = responseDb.getU_contract_pk();
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
		
		if(contractId != null) {
			return true;
		}
		
		return false;
	}
	
	/*Creation of new contract.*/
	public ContractBeanDb addNewContract(ContractGetInfoBean newCont) {
		
		ContractBeanDb responseNewContr = new ContractBeanDb();
		
		ContractBeanDbBuilder newContract = ContractBeanDb
				.builder()
				.u_employee_fk(newCont.getEmployee_id())
				.u_contract_type_fk(newCont.getContract_type())
				.u_date_from(newCont.getDate_from())
				.u_date_to(newCont.getDate_to())
				.u_salary_per_day(newCont.getSalary_per_day())
				.u_is_active(newCont.getIs_active())
				.u_date_created(newCont.getDate_created());
		responseNewContr = contractRepo.save(newContract.build());
		
		return responseNewContr;
	}
	
	/*Creation new employee*/
	public EmployeeBeanDb addNewEmployee(EmployeeGetInfoBean newEmp) {
		
		EmployeeBeanDb responseNewEmp = new EmployeeBeanDb();
		
		EmployeeBeanDbBuilder newEmployee = EmployeeBeanDb
				.builder()
				.emp_tax_id_number(newEmp.getTax_id_number())
				.emp_name(newEmp.getName())
				.emp_last_name(newEmp.getLast_name())
				.emp_birthdate(newEmp.getBirthdate())
				.emp_email(newEmp.getCellphone())
				.emp_cellphone(newEmp.getCellphone())
				.emp_is_active(newEmp.getIs_active())
				.emp_date_created(newEmp.getDate_created());
		responseNewEmp = employeeRepo.save(newEmployee.build());
		
		return responseNewEmp;
	}

	/*Validation of RFC*/
	public Boolean validatorRFC(String taxIdNumb) {
		
		String regex = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-"
									+ "9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$";
		
		return taxIdNumb.matches(regex);
	}
	
	/*Find tax id number by employee*/
	public Boolean checkTaxIdNumExist(String taxIdNum) {
		
		EmployeeBeanDb responseDb = new EmployeeBeanDb();
		String taxNumb = null;
		
		try {
			responseDb = employeeRepo.getInfoByTaxIdNum(taxIdNum);
			taxNumb = responseDb.getEmp_tax_id_number();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		if(taxNumb == null) {
			return false;
		}
		
		return true;
	}
	
	/*Get list of active employees*/
	public ArrayList<EmployeeBeanDb> findListOfEmpActive() {
		
		ArrayList<EmployeeBeanDb> listEmployees = new ArrayList<>();
		
		listEmployees = employeeRepo.getListOfActiveEmp();
		
		return listEmployees;
		
	}
}
