package com.methaphorce_challenge.beansJson;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeGetInfoBean {

	private Integer employee_id; 	//Primary Key auto-incremental id
	private String tax_id_number;	//Employee tax id number
	private String name;			//Employee name
	private String last_name;		//Employee last name
	private Timestamp birthdate;	//Employee birthdate
	private String email;			//Employee email
	private String cellphone;		//Employee cell phone number
	private Boolean is_active;		//Is active the employee?
	private Timestamp date_created;	//Date of creation of the registry
	
}
