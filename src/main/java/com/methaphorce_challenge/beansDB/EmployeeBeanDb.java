/*
 *	Name Class: EmployeeBeanDb 
 *	Author: Francisco Javier Gutierrez Aldrete
 *	Version: 0.0.1 ALPHA
 *	Date creation: 2022-07-29
 * 	Description: Bean to parse the database information.
 */
package com.methaphorce_challenge.beansDB;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employee")
public class EmployeeBeanDb {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@OneToMany(mappedBy = "u_employee_fk", fetch = FetchType.LAZY, orphanRemoval = false)
	private Integer emp_employee_pk; 	//Primary Key auto-incremental id
	private String emp_tax_id_number;	//Employee tax id number
	private String emp_name;			//Employee name
	private String emp_last_name;		//Employee last name
	private Timestamp emp_birthdate;	//Employee birthdate
	private String emp_email;			//Employee email
	private String emp_cellphone;		//Employee cell phone number
	private Boolean emp_is_active;		//Is active the employee?
	private Timestamp emp_date_created;	//Date of creation of the registry
	
}
