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
public class ContractTypeGetInfoBean {

	private Short type_id;			//Primary Key auto-incremental id
	private String name;			//Contract name of contract type
	private String description;	//Contract description
	private Timestamp entry_date;	//Date of creation of the registry
	private Boolean is_active;		//Is active the employee?
	
}
