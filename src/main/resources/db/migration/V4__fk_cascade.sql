ALTER TABLE route
    DROP CONSTRAINT IF EXISTS route_carrier_id_fkey;
ALTER TABLE route
    ADD CONSTRAINT route_carrier_id_fkey
        FOREIGN KEY (carrier_id) REFERENCES carrier(id)
            ON DELETE CASCADE;

ALTER TABLE ticket
    DROP CONSTRAINT IF EXISTS ticket_route_id_fkey;
ALTER TABLE ticket
    ADD CONSTRAINT ticket_route_id_fkey
        FOREIGN KEY (route_id) REFERENCES route(id)
            ON DELETE CASCADE;

ALTER TABLE ticket_purchase
    DROP CONSTRAINT IF EXISTS ticket_purchase_ticket_id_fkey;
ALTER TABLE ticket_purchase
    ADD CONSTRAINT ticket_purchase_ticket_id_fkey
        FOREIGN KEY (ticket_id) REFERENCES ticket(id)
            ON DELETE CASCADE;

ALTER TABLE ticket_purchase
    DROP CONSTRAINT IF EXISTS ticket_purchase_user_id_fkey;
ALTER TABLE ticket_purchase
    ADD CONSTRAINT ticket_purchase_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES app_user(id)
            ON DELETE CASCADE;