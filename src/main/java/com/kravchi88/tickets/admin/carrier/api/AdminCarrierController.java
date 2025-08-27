package com.kravchi88.tickets.admin.carrier.api;

import com.kravchi88.tickets.admin.carrier.api.dto.AdminCarrierRequest;
import com.kravchi88.tickets.admin.carrier.api.mapper.AdminCarrierWebMapper;
import com.kravchi88.tickets.admin.carrier.repository.AdminCarrierRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/admin/carriers")
public class AdminCarrierController {
    private final AdminCarrierRepository repository;
    private final AdminCarrierWebMapper mapper;

    public AdminCarrierController(AdminCarrierRepository repository, AdminCarrierWebMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody AdminCarrierRequest request) {
        long id = repository.insert(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/admin/carriers/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody AdminCarrierRequest request) {
        repository.update(id, mapper.toDomain(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}