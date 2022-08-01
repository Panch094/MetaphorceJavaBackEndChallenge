/*
 *	Name Class: ContractTypeBeanDb 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Bean to parse the database information.
 */
package com.methaphorce_challenge.beansDB;

import java.beans.JavaBean;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JavaBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contracttype")
public class ContractTypeBeanDb {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@OneToMany(mappedBy = "u_contract_type_fk", fetch = FetchType.LAZY, orphanRemoval = false)
	private Short contract_type_pk;		//Primary Key auto-incremental id
	private String contract_name;		//Contract name of contract type
	private String contract_description;//Contract description
	private Timestamp contract_entry_date;	//Date of creation of the registry
	private Boolean contract_is_active;	//Is active the employee?

}
