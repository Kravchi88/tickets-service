package com.kravchi88.tickets.ticket.api;

import com.kravchi88.tickets.common.page.PageResponse;
import com.kravchi88.tickets.ticket.api.dto.TicketResponse;
import com.kravchi88.tickets.ticket.api.mapper.TicketWebMapper;
import com.kravchi88.tickets.ticket.application.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Validated
@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Поиск доступных билетов")
public class TicketController {
    private final TicketService service;
    private final TicketWebMapper mapper;

    public TicketController(TicketService service, TicketWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/available")
    @Operation(summary = "Доступные билеты", description = "Поиск доступных для покупки билетов с фильтрами и пагинацией")
    public ResponseEntity<PageResponse<TicketResponse>> searchAvailableTickets(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String carrier,
            @RequestParam(required = false)
            @FutureOrPresent
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromTime,
            @RequestParam(required = false)
            @FutureOrPresent
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toTime,
            @Min(0) @RequestParam(defaultValue = "0") Integer page,
            @Min(1) @Max(50) @RequestParam(defaultValue = "10") Integer size) {

        if (fromTime != null && toTime != null && fromTime.isAfter(toTime)) {
            throw new IllegalArgumentException("Invalid date filter. fromTime must be before or equal to toTime");
        }

        var data = service.
                searchAvailableTickets(mapper.toSearchParams(origin, destination, carrier, fromTime, toTime, page, size));
        PageResponse<TicketResponse> body = mapper.toResponsePage(data);
        return ResponseEntity.ok(body);
    }
}