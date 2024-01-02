package com.busservice.routeapp.service;

import com.busservice.routeapp.model.Bus;
import javax.servlet.http.PushBuilder;
import java.util.List;

public interface BusService {

    public void addBus(Bus bus);

    public void updateBus(Integer busId, Bus bus);

    public void deleteBus(Integer busId);

    public List<Bus> getAllBuses();
}
