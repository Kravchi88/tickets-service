TRUNCATE TABLE
    ticket_purchase,
    ticket,
    route,
    app_user,
    carrier
    RESTART IDENTITY CASCADE;


--Adm1nPassw0rd
INSERT INTO app_user (login, password_hash, full_name, role)
VALUES ('admin', '$2a$10$kiDp8fJaYIU2Eu5tDXJowu218BQFyXT9IVG2f04mBsIiCl7Rjntcq', 'System Admin', 'ROLE_ADMIN');



INSERT INTO carrier (carrier_name, phone) VALUES
    ('Turkish Airlines', '+90 212 463 63 63'),
    ('Emirates',          '+971 600 555555'),
    ('British Airways',   '+44 344 493 0787'),
    ('Singapore Airlines','+65 6223 8888'),
    ('American Airlines', '+1 800 433 7300');



INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Istanbul', 'Dubai', c.id, 270 FROM carrier c WHERE c.carrier_name='Turkish Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Istanbul', 'London', c.id, 250 FROM carrier c WHERE c.carrier_name='Turkish Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Istanbul', 'Singapore', c.id, 660 FROM carrier c WHERE c.carrier_name='Turkish Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'London', 'Dubai', c.id, 420 FROM carrier c WHERE c.carrier_name='Emirates';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Dubai', 'Singapore', c.id, 420 FROM carrier c WHERE c.carrier_name='Emirates';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Dubai', 'Istanbul', c.id, 270 FROM carrier c WHERE c.carrier_name='Emirates';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'London', 'Dubai', c.id, 415 FROM carrier c WHERE c.carrier_name='British Airways';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'London', 'New York', c.id, 435 FROM carrier c WHERE c.carrier_name='British Airways';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'London', 'Singapore', c.id, 795 FROM carrier c WHERE c.carrier_name='British Airways';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Singapore', 'Dubai', c.id, 420 FROM carrier c WHERE c.carrier_name='Singapore Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Singapore', 'London', c.id, 800 FROM carrier c WHERE c.carrier_name='Singapore Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Singapore', 'Istanbul', c.id, 675 FROM carrier c WHERE c.carrier_name='Singapore Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'New York', 'London', c.id, 420 FROM carrier c WHERE c.carrier_name='American Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'New York', 'Dubai', c.id, 720 FROM carrier c WHERE c.carrier_name='American Airlines';

INSERT INTO route (origin, destination, carrier_id, duration_minutes)
SELECT 'Dallas', 'Miami', c.id, 180 FROM carrier c WHERE c.carrier_name='American Airlines';



INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-01 09:00:00+00', '12A', 35000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Dubai' AND c.carrier_name='Emirates';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-02 09:00:00+00', '12B', 36000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Dubai' AND c.carrier_name='Emirates';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-01 10:30:00+00', '14C', 34000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Dubai' AND c.carrier_name='British Airways';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-03 10:30:00+00', '15D', 35500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Dubai' AND c.carrier_name='British Airways';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-15 08:00:00+03', '10A', 22000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Istanbul' AND r.destination='Dubai' AND c.carrier_name='Turkish Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-16 08:00:00+03', '10B', 23000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Istanbul' AND r.destination='Dubai' AND c.carrier_name='Turkish Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-11-05 14:00:00+04', '22A', 30000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Dubai' AND r.destination='Singapore' AND c.carrier_name='Emirates';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-11-06 14:00:00+04', '22B', 30500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Dubai' AND r.destination='Singapore' AND c.carrier_name='Emirates';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-12-01 07:00:00+00', '3A', 52000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Singapore' AND c.carrier_name='British Airways';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-12-03 07:00:00+00', '3B', 52500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='London' AND r.destination='Singapore' AND c.carrier_name='British Airways';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-12-10 22:00:00+08', '18C', 51000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Singapore' AND r.destination='London' AND c.carrier_name='Singapore Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-12-12 22:00:00+08', '18D', 51500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Singapore' AND r.destination='London' AND c.carrier_name='Singapore Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-20 12:00:00-04', '7A', 41000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='New York' AND r.destination='London' AND c.carrier_name='American Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-10-21 12:00:00-04', '7B', 41500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='New York' AND r.destination='London' AND c.carrier_name='American Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-05 09:30:00-05', '20A', 12000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Dallas' AND r.destination='Miami' AND c.carrier_name='American Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-06 09:30:00-05', '20B', 12500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Dallas' AND r.destination='Miami' AND c.carrier_name='American Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-18 06:45:00+03', '16F', 20000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Istanbul' AND r.destination='London' AND c.carrier_name='Turkish Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-09-19 06:45:00+03', '16E', 20500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Istanbul' AND r.destination='London' AND c.carrier_name='Turkish Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-11-15 21:15:00+08', '5C', 33000
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Singapore' AND r.destination='Istanbul' AND c.carrier_name='Singapore Airlines';

INSERT INTO ticket (route_id, departure_ts, seat_number, price)
SELECT r.id, '2025-11-17 21:15:00+08', '5D', 33500
FROM route r JOIN carrier c ON r.carrier_id=c.id
WHERE r.origin='Singapore' AND r.destination='Istanbul' AND c.carrier_name='Singapore Airlines';