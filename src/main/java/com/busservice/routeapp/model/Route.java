package com.busservice.routeapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @NotNull(message = "Route Id cannot be null")
    private Integer routeId;


    @NotBlank(message = "Route Name cannot be blank")
    private String routeName;



}
