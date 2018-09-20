DROP TABLE IF EXISTS demo.hotels;

CREATE TABLE demo.hotels (id text PRIMARY KEY, name text, country text, city text, owner text);

CREATE INDEX IF NOT EXISTS HOTEL_CITY ON demo.hotels ( city );

INSERT INTO demo.hotels(id, name, country, city, owner) values('hilton-usa-1', 'Hilton', 'USA', 'New York', 'Conrad Hilton');
INSERT INTO demo.hotels(id, name, country, city, owner) values('hilton-usa-2', 'Hilton', 'USA', 'Las Vegas', 'Conrad Hilton');
INSERT INTO demo.hotels(id, name, country, city, owner) values('paradise-usa-1', 'Paradise Hotel', 'USA', 'California', 'Jon Jonson');
INSERT INTO demo.hotels(id, name, country, city, owner) values('villa-usa-1', 'Villa Sunrise', 'USA', 'California', 'Michael Michaelson');
INSERT INTO demo.hotels(id, name, country, city, owner) values('hotel-1111', 'Bungalo house', 'USA', 'Miami', 'Donald Trump');

DROP TABLE IF EXISTS demo.rooms;

CREATE TABLE demo.rooms (hotel_id text, floor int, room_number int, square decimal, price decimal, PRIMARY KEY (hotel_id, room_number));

insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 1, 100.0, 100.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 2, 100.0, 100.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 3, 100.0, 100.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 4, 100.0, 100.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 5, 100.0, 100.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 1, 1, 120.0, 200.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 2, 2, 120.0, 200.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 2, 3, 120.0, 200.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 2, 4, 120.0, 200.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-1', 2, 5, 120.0, 200.0);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-2', 1, 1, 50, 80.50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-2', 1, 2, 50, 80.50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-2', 1, 3, 50, 80.50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-2', 1, 4, 50, 80.50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hilton-usa-2', 1, 5, 50, 80.50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('paradise-usa-1', 1, 1, 70.5, 130.6);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('paradise-usa-1', 1, 2, 70.5, 130.6);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('paradise-usa-1', 1, 3, 70.5, 130.6);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('paradise-usa-1', 1, 4, 70.5, 130.6);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('paradise-usa-1', 1, 5, 70.5, 130.6);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('villa-usa-1', 1, 1, 30, 50);
insert into demo.rooms(hotel_id, floor, room_number, square, price) values ('hotel-1111', 1, 1, 80, 150.0);

DROP TABLE IF EXISTS demo.guests;

CREATE TABLE demo.guests(login text PRIMARY KEY, first_name text, last_name text);

insert into demo.guests(login, first_name, last_name) values ('alexlitvinovpost@gmail.com', 'Alexander', 'Litvinov');
insert into demo.guests(login, first_name, last_name) values ('ivanivanov@gmail.com', 'Ivanov', 'Ivan');
insert into demo.guests(login, first_name, last_name) values ('petrovpetr@gmail.com', 'Petrov', 'Petr');

DROP TABLE IF EXISTS demo.bookings;

CREATE TABLE demo.bookings (hotel_id text, start_date timestamp, end_date timestamp, login text, room int, price double, guest_number int, PRIMARY KEY ((hotel_id), start_date, end_date, room));

insert into demo.bookings(hotel_id, start_date, end_date, room, price, guest_number, login) values('hilton-usa-1', '2017-05-05', '2017-05-08', 1, 100.0, 1, 'ivanivanov@gmail.com');
insert into demo.bookings(hotel_id, start_date, end_date, room, price, guest_number, login) values('hilton-usa-1', '2017-05-09', '2017-05-12', 1, 100.0, 1, 'ivanivanov@gmail.com');
insert into demo.bookings(hotel_id, start_date, end_date, room, price, guest_number, login) values('hilton-usa-1', '2017-05-13', '2017-05-15', 1, 100.0, 1, 'ivanivanov@gmail.com');

DROP TABLE IF EXISTS demo.bookings_period_detail;

CREATE TABLE demo.bookings_period_detail (hotel_id text, booking_date timestamp, room int, start_date timestamp, end_date timestamp, PRIMARY KEY ((hotel_id, room), booking_date));

insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-05', 1, '2017-05-05', '2017-05-08');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-06', 1, '2017-05-05', '2017-05-08');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-07', 1, '2017-05-05', '2017-05-08');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-08', 1, '2017-05-05', '2017-05-08');

insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-09', 1, '2017-05-09', '2017-05-12');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-10', 1, '2017-05-09', '2017-05-12');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-11', 1, '2017-05-09', '2017-05-12');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-12', 1, '2017-05-09', '2017-05-12');

insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-13', 1, '2017-05-13', '2017-05-15');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-14', 1, '2017-05-13', '2017-05-15');
insert into demo.bookings_period_detail(hotel_id, booking_date, room, start_date, end_date) values('hilton-usa-1', '2017-05-15', 1, '2017-05-13', '2017-05-15');
