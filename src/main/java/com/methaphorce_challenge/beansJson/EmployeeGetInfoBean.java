package com.methaphorce_challenge.beansJson;

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
@JsonPropertyOrder(value = {"EmployeeId", "TaxIdNumber", "Name", "LastName", 
							"Birthdate", "Email", "Cellphone", "IsActive",
							"DateCreated"
							})
public class EmployeeGetInfoBean {

//	@JsonProperty("EmployeeId")
//	private Integer employee_id; 	//Primary Key auto-incremental id
	
	@JsonProperty("TaxIdNumber")
	private String tax_id_number;	//Employee tax id number
	
	@JsonProperty("Name")
	private String name;			//Employee name
	
	@JsonProperty("LastName")
	private String last_name;		//Employee last name
	
	@JsonProperty("Birthdate")
	private Timestamp birthdate;	//Employee birthdate
	
	@JsonProperty("Email")
	private String email;			//Employee email
	
	@JsonProperty("Cellphone")
	private String cellphone;		//Employee cell phone number
	
	@JsonProperty("IsActive")
	private Boolean is_active;		//Is active the employee?
	
	@JsonProperty("DateCreated")
	private Timestamp date_created;	//Date of creation of the registry
	
}
