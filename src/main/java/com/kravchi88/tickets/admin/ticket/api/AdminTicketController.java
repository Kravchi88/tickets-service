package com.kravchi88.tickets.admin.ticket.api;

import com.kravchi88.tickets.admin.ticket.api.dto.AdminTicketRequest;
import com.kravchi88.tickets.admin.ticket.api.mapper.AdminTicketWebMapper;
import com.kravchi88.tickets.admin.ticket.repository.AdminTicketRepository;
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
@RequestMapping("/api/admin/tickets")
@Tag(name = "Admin: Tickets", description = "Управление билетами (роль ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class AdminTicketController {
    private final AdminTicketRepository repository;
    private final AdminTicketWebMapper mapper;

    public AdminTicketController(AdminTicketRepository repository, AdminTicketWebMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Создать билет")
    public ResponseEntity<Void> create(@Valid @RequestBody AdminTicketRequest request) {
        long id = repository.insert(mapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/admin/tickets/" + id)).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить билет")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody AdminTicketRequest request) {
        repository.update(id, mapper.toDomain(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить билет")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}