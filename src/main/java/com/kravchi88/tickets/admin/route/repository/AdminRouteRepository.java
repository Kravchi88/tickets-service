package com.kravchi88.tickets.admin.route.repository;

import com.kravchi88.tickets.admin.route.model.AdminRoute;

public interface AdminRouteRepository {
    long insert(AdminRoute route);
    void update(long id, AdminRoute route);
    void delete(long id);
}