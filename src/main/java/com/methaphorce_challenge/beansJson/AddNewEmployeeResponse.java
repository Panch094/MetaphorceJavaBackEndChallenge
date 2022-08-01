package com.methaphorce_challenge.beansJson;

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
@JsonPropertyOrder(value = {"Identifier","EmployeeAction",
							"EmployeeAdded"
						    })
public class AddNewEmployeeResponse {

	@JsonProperty("Identifier") 
	private String identifier;		//identifier contract id or employee id
	
	@JsonProperty("EmployeeAction") 
	private String employeeAction;	//Type of action towards employee
	
	@JsonProperty("EmployeeAdded") 
	private EmployeeGetInfoBean employeeAdded;	//Contract complete.
	
}
