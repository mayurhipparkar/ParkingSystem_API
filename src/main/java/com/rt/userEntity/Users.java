package com.rt.userEntity;

import java.time.LocalDateTime;
import java.util.List;

import com.rt.vehicleEntryEntity.Vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	 @Id
	 @GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	 
	 @Column(name = "fullname", nullable = false)
	private String fullname;
	 
	  @Column(name = "email", nullable = false, unique = true)
	private String email;
	  
	  @Column(name = "number", nullable = false, unique = true)
	private String number;
	  
	  @Column(name = "address")
	private String address;
	  
	  @Column(name = "password", nullable = false)
	private String password;
	  	
	  @Column(name = "role",nullable = false)
	  private String role;
	  
	  @Column(name = "status",nullable = false)
	  private String status;

	  @OneToMany(mappedBy = "users")
	    private List<Vehicle> vehicles;
	  
	  @Column(name = "created_time", updatable = false)
	    private LocalDateTime createdTime;

	    @Column(name = "modified_time")
	    private LocalDateTime modifiedTime;
	  
	    //for signup form
	public Users(String fullname, String email, String number, String address, String password, String role,String status) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.number = number;
		this.address = address;
		this.password = password;
		this.role = role;
		this.status=status;
	}
	
	//this is used to add guard only in admin dashboard.
	public Users(String fullname, String email, String number, String address, String password,String status) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.number = number;
		this.address = address;
		this.password = password;
		this.status=status;
	}
	
	
	public Users(String fullname, String email, String number, String address, String password) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.number = number;
		this.address = address;
		this.password = password;
	}
	
	//it is used to add time stamp when we create any guard or admin.
	 @PrePersist
	    public void onCreate() {
	        this.createdTime = LocalDateTime.now();
	        this.modifiedTime = LocalDateTime.now(); // Optional: also set on create
	    }
	//it is used to update time stamp when we update any guard or admin.
	    @PreUpdate
	    public void onUpdate() {
	        this.modifiedTime = LocalDateTime.now();
	    }

	
	
}
