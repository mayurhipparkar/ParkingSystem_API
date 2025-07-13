package com.rt.vehicleEntryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.rt.userEntity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="vehicle_entries")
public class Vehicle {
	@Id
	 @GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	
	 @Column(name = "vehicle_type", nullable = false)
	private String vehicleType;
	 
	 @Column(name = "vehicle_number", nullable = false)
	private String vehicleNumber;
	 
	 @Column(name = "owner_name", nullable = false)
	private String ownerName;
	 
	 @Column(name = "contact_number", nullable = false)
	 private String contactNumber;
	
	 @Column(name = "entry_date", nullable = false)
	private LocalDate entryDate;
	
	 @Column(name = "entry_time", nullable = false)
	private LocalTime entryTime;
	 
	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false)
	 private Users users; 
	 
	 @Column(name = "created_time", updatable = false)
	    private LocalDateTime createdTime;

	    @Column(name = "modified_time")
	    private LocalDateTime modifiedTime;
	
	 public Vehicle(String vehicleType, String vehicleNumber, String ownerName, String contactNumber,
			LocalDate entryDate, LocalTime entryTime, Users users) {
		super();
		this.vehicleType = vehicleType;
		this.vehicleNumber = vehicleNumber;
		this.ownerName = ownerName;
		this.contactNumber = contactNumber;
		this.entryDate = entryDate;
		this.entryTime = entryTime;
		this.users = users;
	}	
	 
	 public Vehicle(int id,String vehicleType, String vehicleNumber, String ownerName, String contactNumber,
				LocalDate entryDate, LocalTime entryTime, Users users) {
			super();
			this.id=id;
			this.vehicleType = vehicleType;
			this.vehicleNumber = vehicleNumber;
			this.ownerName = ownerName;
			this.contactNumber = contactNumber;
			this.entryDate = entryDate;
			this.entryTime = entryTime;
			this.users = users;
		}	
	 
	  @PrePersist
	    public void onCreate() {
	        this.createdTime = LocalDateTime.now();
	        this.modifiedTime = LocalDateTime.now(); // Optional: also set on create
	    }

	    @PreUpdate
	    public void onUpdate() {
	        this.modifiedTime = LocalDateTime.now();
	    }

}
