/*-- the password hash is generated by BCrypt  '123'
INSERT INTO APP.USERS (id, username, password, first_name, last_name, email, phone_number, enabled, last_password_reset_date) VALUES (1, 'user', '$2a$04$AL6GnWTfmJyQL6Bh2Vknvu4G3INyVQRCaozDT715lZvj/DXPtxjUe', 'ali', 'mir', 'user@example.com', '09120000000', true, '2024-09-14 21:58:58.508-07');
INSERT INTO APP.USERS (id, username, password, first_name, last_name, email, phone_number, enabled, last_password_reset_date) VALUES (2, 'admin', '$2a$04$AL6GnWTfmJyQL6Bh2Vknvu4G3INyVQRCaozDT715lZvj/DXPtxjUe', 'shayan', 'mir', 'admin@example.com', '09129231440', true, '2024-09-14 18:57:58.508-07');

INSERT INTO APP.AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO APP.AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO APP.USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO APP.USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO APP.USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
*/