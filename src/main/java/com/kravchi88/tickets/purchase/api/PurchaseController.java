package com.kravchi88.tickets.purchase.api;

import com.kravchi88.tickets.purchase.api.dto.PurchasedTicketResponse;
import com.kravchi88.tickets.purchase.api.mapper.PurchaseWebMapper;
import com.kravchi88.tickets.purchase.application.PurchaseService;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/api")
public class PurchaseController {
    private final PurchaseService service;
    private final PurchaseWebMapper mapper;

    public PurchaseController(PurchaseService service, PurchaseWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/tickets/{ticketId}/purchase")
    public ResponseEntity<PurchasedTicketResponse> purchase(
            @Min(1) @RequestHeader("X-User-Id") long userId,
            @Min(1) @PathVariable("ticketId") long ticketId) {
        var purchasedTicket = service.purchaseTicket(mapper.toPurchaseTicketCommand(userId, ticketId));
        PurchasedTicketResponse body = mapper.toPurchasedResponse(purchasedTicket);
        URI location = URI.create("/api/purchases/" + body.purchaseId());
        return ResponseEntity.created(location).body(body);
    }
}
