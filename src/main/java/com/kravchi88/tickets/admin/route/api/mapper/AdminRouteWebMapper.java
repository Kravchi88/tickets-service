package com.kravchi88.tickets.admin.route.api.mapper;

import com.kravchi88.tickets.admin.route.api.dto.AdminRouteRequest;
import com.kravchi88.tickets.admin.route.model.AdminRoute;
import org.springframework.stereotype.Component;

@Component
public class AdminRouteWebMapper {
    public AdminRoute toDomain(AdminRouteRequest request) {
        return new AdminRoute(request.origin(), request.destination(), request.carrierId(), request.durationMinutes());
    }
}