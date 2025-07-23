package com.rt.vehicleAndParkingEntity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.rt.allEnums.ParkingStatus;
import com.rt.slotEntity.FourWheelerSlots;
import com.rt.slotEntity.TwoWheelerSlots;
import com.rt.userEntity.Users;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_sessions")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Reference to vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // Slot references (only one of them will be non-null)
    @ManyToOne
    @JoinColumn(name = "two_wheeler_slot_id")
    private TwoWheelerSlots twoWheelerSlot;

    @ManyToOne
    @JoinColumn(name = "four_wheeler_slot_id")
    private FourWheelerSlots fourWheelerSlot;
    
    
 // Reference to user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // In and Out Date/Time
    @Column(name = "in_date", nullable = false)
    private LocalDate inDate;

    @Column(name = "in_time", nullable = false)
    private LocalTime inTime;

    @Column(name = "out_date")
    private LocalDate outDate;

    @Column(name = "out_time")
    private LocalTime outTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParkingStatus status;
    
}
