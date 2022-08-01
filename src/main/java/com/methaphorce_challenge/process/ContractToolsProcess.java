/*
 *	Name Class: ContractToolsProcess 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Class of the tools necessary to read data in the DB.
 */
package com.methaphorce_challenge.process;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.methaphorce_challenge.beansDB.ContractBeanDb;
import com.methaphorce_challenge.beansDB.ContractTypeBeanDb;
import com.methaphorce_challenge.beansDB.EmployeeBeanDb;
import com.methaphorce_challenge.beansJson.EmployeeActiveResponse;
import com.methaphorce_challenge
				.beansJson
				.EmployeeActiveResponse
				.EmployeeActiveResponseBuilder;
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
	ContractRepository contractRepo;
	
	@Autowired
	ContractTypeRepository contractTypeRepo;
	
	@Autowired
	EmployeeRepository employeeRepo;
	
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
			response = contractRepo.getInfoContrById(id_contract);
		}catch (Exception e) {
			buildGenericResponse(shortToLong(id_contract), e.getMessage(), false);
		}
		
		return response;	
	}
	
	/*Gets contract type information.*/
	public ContractTypeBeanDb getInfoTypeContract(Short contractId) {
		
		ContractTypeBeanDb response = new ContractTypeBeanDb();
		
		try {
			response = contractTypeRepo.getInfoContTypeById(contractId);
		} catch (Exception e) {
			buildGenericResponse(shortToLong(contractId), e.getMessage(), false);
		}
		
		return response;
	}
	
	/*Gets employee information.*/
	public EmployeeBeanDb getInfoEmployee(Integer employeeId) {
		
		EmployeeBeanDb response = new EmployeeBeanDb();
		Long contractId = shortToLong(getContractId(employeeId));
		
		try {
			
		} catch (Exception e) {
			buildGenericResponse(contractId, e.getMessage(), false);
		}
		
		return response;		
	}
	
	/*Review of credentials*/
	public Boolean credentialVerificationOtherValildations(String authorizer, Long contractId) {
		
		String[] tokenAuth = authorizer.split(" "), convinationUp;
		Base64.Decoder decoder = Base64.getDecoder();
		String getByteAbove;
		
		if(tokenAuth[0].equalsIgnoreCase("Basic")){
			
			byte[] byteArrayDecoder = decoder.decode(tokenAuth[1]);
			getByteAbove = new String(byteArrayDecoder);
			convinationUp = getByteAbove.split(":");
			
			if(convinationUp[0].equalsIgnoreCase("challengeUserMetaphorce")) {
				if(convinationUp[1].equalsIgnoreCase("challengePass2022")){
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
	
	/*Build generic message.*/
	public EmployeeActiveResponse buildSpecificResponse(
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
		
		Integer response = null;
		
		try {
			response = shortToInteger(getInfoContract(longToShort(contractId))
														   .getU_employee_fk());
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Wrong contract id"
			);
		}
		return response;
	}
	
	/*Gets contract id by employee id*/
	public Short getContractId(Integer employeeId) {
		
		Short response = null;
		
		try {
			response = contractRepo.getInfoContrByEmployee(
								 integerToShort(employeeId)).getU_contract_pk();
		} catch (Exception e) {
			buildGenericResponse(shortToLong(response), e.getMessage(), false);
		}
		
		return response;
		
	}
	
	/*Gets employee full name*/
	public String employeeFullName(Long contractId) {
		
		return getInfoEmployee(getEmployeeId(contractId)).getEmp_name()
			   + " " + 
			   getInfoEmployee(getEmployeeId(contractId)).getEmp_last_name();
	}
}
