package com.kravchi88.tickets.admin.carrier.api.mapper;

import com.kravchi88.tickets.admin.carrier.api.dto.AdminCarrierRequest;
import com.kravchi88.tickets.admin.carrier.model.AdminCarrier;
import org.springframework.stereotype.Component;

@Component
public class AdminCarrierWebMapper {
    public AdminCarrier toDomain(AdminCarrierRequest request) {
        return new AdminCarrier(request.name(), request.phone());
    }
}