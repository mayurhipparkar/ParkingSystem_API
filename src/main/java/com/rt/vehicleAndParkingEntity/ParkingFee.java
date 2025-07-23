package com.rt.vehicleAndParkingEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

@Entity
@Table(name = "parking_fee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "hourly_fee", precision = 10, scale = 2)
    private BigDecimal hourlyFee;

    @Column(name = "daily_fee", precision = 10, scale = 2)
    private BigDecimal dailyFee;
    
    // Reference to user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    // Timestamps
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @PrePersist
    public void onCreate() {
        this.createdTime = LocalDateTime.now();
        this.modifiedTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedTime = LocalDateTime.now();
    }


}
