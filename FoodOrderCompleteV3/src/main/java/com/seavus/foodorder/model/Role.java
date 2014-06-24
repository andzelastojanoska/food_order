package com.seavus.foodorder.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity  
@Table(name="roles")  
public class Role {  
  
    @Id  
    @GeneratedValue  
    private Long id;  
      
    private String role;  
      
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)  
    private List<Employee> employees;  
    
    public Role() {}
    
    public Role (String role) {
    	this.role = role;
    }
      
    public Long getId() {  
        return id;  
    }  
    public void setId(Long id) {  
        this.id = id;  
    }  
    public String getRole() {  
        return role;  
    }  
    public void setRole(String role) {  
        this.role = role;  
    }  
    public List<Employee> getEmployees() {  
        return employees;  
    }  
    public void setEmployees(List<Employee> employees) {  
        this.employees = employees;  
    }  
      
}  