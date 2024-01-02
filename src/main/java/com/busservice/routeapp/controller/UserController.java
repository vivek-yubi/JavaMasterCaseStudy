package com.busservice.routeapp.controller;

import com.example.casestudy.dto.EntityResponseDto;
import com.example.casestudy.model.Bus;
import com.example.casestudy.model.Route;
import com.example.casestudy.service.BusService;
import com.example.casestudy.service.RouteService;
import com.example.casestudy.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private RouteService routeService;

    @Autowired
    private BusService busService;

    @Autowired
    private TripService tripService;


    @GetMapping("/getAllRoutes")
    public ResponseEntity<EntityResponseDto> getAllRoutes(@RequestHeader(value = "auth_token")  String authToken) {
        try
        {
            List<Route> returnedRoutes= routeService.getAllRoutes();
            return ResponseEntity.ok().body(EntityResponseDto.builder().message("Routes returned successfully").data(returnedRoutes).build());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(EntityResponseDto.builder().message(e.getMessage()).build());
        }
    }



    @GetMapping("/getAllBuses")
    public ResponseEntity<EntityResponseDto> getAllBuses(@RequestHeader(value = "auth_token")  String authToken) {
        try
        {
            List<Bus> returnedBuses= busService.getAllBuses();
            return ResponseEntity.ok().body(EntityResponseDto.builder().message("Buses returned successfully").data(returnedBuses).build());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(EntityResponseDto.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping("/getBusesForRoute/{routeId}")
    public ResponseEntity<EntityResponseDto> getBusesForRoute(@RequestHeader(value = "auth_token")  String authToken, @PathVariable Integer routeId) {
        try
        {
            HashMap<String,Object> returnedBuses= tripService.getBusesForRoute(routeId);
            return ResponseEntity.ok().body(EntityResponseDto.builder().message("Buses associated with the provided tripId returned successfully").data(returnedBuses).build());
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(EntityResponseDto.builder().message(e.getMessage()).build());
        }
    }

}
