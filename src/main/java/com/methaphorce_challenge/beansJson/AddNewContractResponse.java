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
@JsonPropertyOrder(value = {"Identifier","ContractAction",
							"ContractAdded"
						    })
public class AddNewContractResponse {

	@JsonProperty("Identifier") 
	private String identifier;		//identifier contract id or employee id
	
	@JsonProperty("ContractAction") 
	private String contractAction;	//Type of action towards contract
	
	@JsonProperty("ContractAdded") 
	private ContractGetInfoBean contractAdded;	//Contract complete.
	
}
