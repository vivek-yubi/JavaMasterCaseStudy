package com.busservice.routeapp.model;

import com.example.casestudy.constants.enums.BusType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer busId;

    @Column(unique = true, name = "bus_reg_number")
    private String regNumber;

    @Column(name = "bus_type")
    @Enumerated(EnumType.STRING)
    private BusType busType;

    // getters and setters
}
