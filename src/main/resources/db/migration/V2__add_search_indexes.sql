CREATE INDEX IF NOT EXISTS idx_route_carrier_id ON route (carrier_id);

CREATE INDEX IF NOT EXISTS idx_ticket_route_id ON ticket (route_id);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_route_origin_trgm
    ON route USING gin (origin gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_route_destination_trgm
    ON route USING gin (destination gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_carrier_name_trgm
    ON carrier USING gin (carrier_name gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_ticket_avail_departure_ts ON ticket (departure_ts)
    WHERE is_sold = false;