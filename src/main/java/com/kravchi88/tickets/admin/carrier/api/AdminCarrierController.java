package com.kravchi88.tickets.admin.carrier.api;

import com.kravchi88.tickets.admin.carrier.api.dto.AdminCarrierRequest;
import com.kravchi88.tickets.admin.carrier.api.mapper.AdminCarrierWebMapper;
import com.kravchi88.tickets.admin.carrier.repository.AdminCarrierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "Admin: Carriers", description = "Управление перевозчиками (роль ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class AdminCarrierController {
    private final AdminCarrierRepository repository;
    private final AdminCarrierWebMapper mapper;

    public AdminCarrierController(AdminCarrierRepository repository, AdminCarrierWebMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Создать перевозчика")
    public ResponseEntity<Void> create(@Valid @RequestBody AdminCarrierRequest request) {
        long id = repository.insert(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/admin/carriers/" + id)).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить перевозчика")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody AdminCarrierRequest request) {
        repository.update(id, mapper.toDomain(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить перевозчика")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}