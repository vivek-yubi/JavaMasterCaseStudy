package com.busservice.routeapp.service;

import com.busservice.routeapp.model.Bus;
import com.example.casestudy.dao.BusRepository;
import com.example.casestudy.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService{

    @Autowired
    private BusRepository busRepository;


    @Override
    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public void updateBus(Integer busId, Bus bus) {
        Optional<Bus> fetchBus = busRepository.findById(busId);
        if(fetchBus.isEmpty())
            throw new RuntimeException("Bus not found to modify details");
        Bus fetchedBus = fetchBus.get();
        fetchedBus.setRegNumber(bus.getRegNumber());
        fetchedBus.setBusType(bus.getBusType());
        busRepository.save(fetchedBus);
    }

    @Override
    public void deleteBus(Integer busId) {
        Optional<Bus> fetchBus = busRepository.findById(busId);
        if(fetchBus.isEmpty())
            throw new RuntimeException("Bus not found to delete");
        busRepository.delete(fetchBus.get());
    }
}
