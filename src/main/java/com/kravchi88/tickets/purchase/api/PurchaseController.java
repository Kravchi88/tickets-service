package com.kravchi88.tickets.purchase.api;

import com.kravchi88.tickets.common.page.PageResponse;
import com.kravchi88.tickets.purchase.api.dto.PurchasedTicketResponse;
import com.kravchi88.tickets.purchase.api.mapper.PurchaseWebMapper;
import com.kravchi88.tickets.purchase.application.PurchaseService;
import com.kravchi88.tickets.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/api")
@Tag(name = "Purchases", description = "Покупка билетов и просмотр купленных билетов")
public class PurchaseController {
    private final PurchaseService service;
    private final PurchaseWebMapper mapper;

    public PurchaseController(PurchaseService service, PurchaseWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/tickets/{ticketId}/purchase")
    @Operation(summary = "Купить билет", description = "Покупка доступного билета текущим пользователем")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PurchasedTicketResponse> purchase(
            @AuthenticationPrincipal UserPrincipal principal,
            @Min(1) @PathVariable("ticketId") long ticketId) {
        var purchasedTicket = service.purchaseTicket(mapper.toPurchaseTicketCommand(principal.userId(), ticketId));
        PurchasedTicketResponse body = mapper.toPurchasedResponse(purchasedTicket);
        URI location = URI.create("/api/purchases/" + body.purchaseId());
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/purchases")
    @Operation(summary = "Список купленных билетов", description = "Возвращает купленные билеты текущего пользователя (пагинация)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PageResponse<PurchasedTicketResponse>> getPurchasedTickets(
            @AuthenticationPrincipal UserPrincipal principal,
            @Min(0) @RequestParam(defaultValue = "0") Integer page,
            @Min(1) @Max(50) @RequestParam(defaultValue = "10") Integer size) {
        var data = service.getPurchasedTickets(mapper.toSearchParams(principal.userId(), page, size));
        PageResponse<PurchasedTicketResponse> body = mapper.toResponsePage(data);
        return ResponseEntity.ok(body);
    }
}
