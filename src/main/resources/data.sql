-- =====================================================================
-- Sample seed data for the Medical Appointment Scheduling System
-- Runs automatically after Hibernate creates the schema
-- (spring.jpa.defer-datasource-initialization=true, spring.sql.init.mode=always)
-- Safe to re-run: it only inserts if the tables are empty.
-- =====================================================================

INSERT INTO persons (id, full_name, email, phone_number, address, created_at, person_type)
SELECT * FROM (SELECT 1, 'Nimal Perera', 'nimal.perera@example.com', '0771234567', '45 Galle Road, Colombo 03', NOW(), 'PATIENT') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM persons WHERE id = 1);

INSERT INTO persons (id, full_name, email, phone_number, address, created_at, person_type)
SELECT * FROM (SELECT 2, 'Kamala Silva', 'kamala.silva@example.com', '0777654321', '12 Kandy Road, Kadawatha', NOW(), 'PATIENT') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM persons WHERE id = 2);

INSERT INTO patients (id, date_of_birth, gender, blood_group, medical_history)
SELECT * FROM (SELECT 1, '1994-03-12', 'Male', 'O+', 'No known allergies.') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM patients WHERE id = 1);

INSERT INTO patients (id, date_of_birth, gender, blood_group, medical_history)
SELECT * FROM (SELECT 2, '1988-07-25', 'Female', 'A+', 'Mild asthma.') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM patients WHERE id = 2);

INSERT INTO persons (id, full_name, email, phone_number, address, created_at, person_type)
SELECT * FROM (SELECT 3, 'Dr. Suresh Fernando', 'suresh.fernando@example.com', '0711112222', 'Nawaloka Hospital, Colombo', NOW(), 'DOCTOR') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM persons WHERE id = 3);

INSERT INTO persons (id, full_name, email, phone_number, address, created_at, person_type)
SELECT * FROM (SELECT 4, 'Dr. Anusha Jayawardena', 'anusha.jaya@example.com', '0713334444', 'Asiri Hospital, Colombo', NOW(), 'DOCTOR') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM persons WHERE id = 4);

INSERT INTO doctors (id, specialization, license_number, consultation_fee, available_days)
SELECT * FROM (SELECT 3, 'Cardiologist', 'SLMC-10234', 4500.00, 'Monday,Wednesday,Friday') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM doctors WHERE id = 3);

INSERT INTO doctors (id, specialization, license_number, consultation_fee, available_days)
SELECT * FROM (SELECT 4, 'Dermatologist', 'SLMC-10897', 3000.00, 'Tuesday,Thursday,Saturday') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM doctors WHERE id = 4);

INSERT INTO persons (id, full_name, email, phone_number, address, created_at, person_type)
SELECT * FROM (SELECT 5, 'Anjana', 'anjana.uni01@gmail.com', '0700000000', 'SLIIT, Malabe', NOW(), 'ADMIN') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM persons WHERE id = 5);

INSERT INTO admins (id, designation)
SELECT * FROM (SELECT 5, 'System Administrator') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM admins WHERE id = 5);

-- Sample appointments (one of each type -> shows polymorphism working)
INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, status, reason_for_visit, created_at, appointment_type)
SELECT * FROM (SELECT 1, 1, 3, CURDATE() + INTERVAL 2 DAY, '10:00:00', 'PENDING', 'Routine heart checkup', NOW(), 'IN_PERSON') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM appointments WHERE id = 1);

INSERT INTO inperson_appointments (id, room_number)
SELECT * FROM (SELECT 1, 'Room 204') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM inperson_appointments WHERE id = 1);

INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, status, reason_for_visit, created_at, appointment_type)
SELECT * FROM (SELECT 2, 2, 4, CURDATE() + INTERVAL 3 DAY, '14:30:00', 'PENDING', 'Skin rash consultation', NOW(), 'ONLINE') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM appointments WHERE id = 2);

INSERT INTO online_appointments (id, meeting_link)
SELECT * FROM (SELECT 2, 'https://meet.mass-clinic.lk/room/2') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM online_appointments WHERE id = 2);
