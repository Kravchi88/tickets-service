package com.kravchi88.tickets.admin.route.api;

import com.kravchi88.tickets.admin.route.api.dto.AdminRouteRequest;
import com.kravchi88.tickets.admin.route.api.mapper.AdminRouteWebMapper;
import com.kravchi88.tickets.admin.route.repository.AdminRouteRepository;
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
@RequestMapping("/api/admin/routes")
public class AdminRouteController {
    private final AdminRouteRepository repository;
    private final AdminRouteWebMapper mapper;

    public AdminRouteController(AdminRouteRepository repository, AdminRouteWebMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody AdminRouteRequest request) {
        long id = repository.insert(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/admin/routes/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody AdminRouteRequest request) {
        repository.update(id, mapper.toDomain(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}