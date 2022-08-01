/*
 *	Name Class: GenericServiceResponse 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Generic service response with http status.
 */
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
@JsonPropertyOrder(value = {"",""})
public class GenericServiceResponse {

	@JsonProperty("ContractId") private Long contract_id;	//Contract id
	@JsonProperty("ResponseMessage") private String message;//Response message
	@JsonProperty("Status") private Boolean responseStatus;	//Response status
	
}
