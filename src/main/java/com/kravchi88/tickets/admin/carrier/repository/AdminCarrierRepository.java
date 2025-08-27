package com.kravchi88.tickets.admin.carrier.repository;

import com.kravchi88.tickets.admin.carrier.model.AdminCarrier;

public interface AdminCarrierRepository {
    long insert(AdminCarrier carrier);
    void update(long id, AdminCarrier carrier);
    void delete(long id);
}