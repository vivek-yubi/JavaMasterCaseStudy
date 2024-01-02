package com.busservice.routeapp.service;




import com.busservice.routeapp.model.Route;

import java.util.List;

public interface RouteService {

    public void addRoute(Route route);

    public List<Route> getAllRoutes();

    public void deleteRoute(int routeId);
}
