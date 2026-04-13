package com.app.quantitymeasurement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double thisValue;

    @Column(nullable = false)
    private String thisUnit;

    @Column(nullable = false)
    private String thisMeasurementType;

    @Column(nullable = false)
    private Double thatValue;

    @Column(nullable = false)
    private String thatUnit;

    @Column(nullable = false)
    private String thatMeasurementType;

    @Column(nullable = false)
    private String operation;

    private String resultString;
    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    @Column(nullable = false)
    private boolean error;

    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    
    //the entity becomes linked to the user who performed the calculation
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}