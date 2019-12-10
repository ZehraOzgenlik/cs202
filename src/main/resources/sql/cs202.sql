DROP DATABASE IF EXISTS cs202;
CREATE DATABASE cs202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE CS202;

#DDL

DROP TABLE IF EXISTS doctor_departments;
DROP TABLE IF EXISTS rest_days;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS treatment_types;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_types;

CREATE TABLE user_types (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE users (
	id VARCHAR(11) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	user_type INT,
    PRIMARY KEY (id),
	FOREIGN KEY (user_type) REFERENCES user_types (id) ON DELETE SET NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE departments (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE doctor_departments (
	user_id VARCHAR(11) NOT NULL,
	department_id INT NOT NULL,
    PRIMARY KEY (user_id, department_id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE rooms (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE treatment_types (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE appointments (
	id INT NOT NULL AUTO_INCREMENT,
	patient_id VARCHAR(11) NOT NULL,
    doctor_id VARCHAR(11) NOT NULL,
    room_id INT NOT NULL,
    treatment_type INT,
	start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (doctor_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE,
    FOREIGN KEY (treatment_type) REFERENCES treatment_types (id) ON DELETE SET NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE rest_days (
	id INT NOT NULL AUTO_INCREMENT,
	start_time DATETIME,
	end_time DATETIME,
	user_id VARCHAR(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

#DML

INSERT INTO user_types (name)
VALUES
("Patient"),
("Doctor"),
("Nurse"),
("Manager");

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES
("76280461060", "SİDAR", "YILDIRIM", SHA1("11111"), 1),
("59962434170", "ADİL", "BİLİCAN", SHA1("11111"), 1),
("46820055516", "ILGIN", "TOPTAŞ", SHA1("11111"), 1),
("37266371162", "CANSUNUR", "ÖZGÜR", SHA1("11111"), 1),
("33537884584", "SEYFİ", "İBRAHİMGİL", SHA1("11111"), 1);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES
("85647403018", "NİLSU", "AYDOĞAN", SHA1("22222"), 2),
("48679641476", "ERİM", "KARAGÖL", SHA1("22222"), 2),
("21764134480", "BERFU", "GÜDER", SHA1("22222"), 2),
("19852145364", "AKİF", "KARPUZ", SHA1("22222"), 2),
("35787960612", "BEDREDDİN", "SEZGİNER", SHA1("22222"), 2);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES
("47073346598", "ELİF", "AKYÜZ", SHA1("33333"), 3),
("65839119170", "TARKAN", "UZUNÇAKMAK", SHA1("33333"), 3),
("27835914784", "ELİFCAN", "KURTULUŞ", SHA1("33333"), 3),
("25268907762", "YUSUFCAN", "EVMEZ", SHA1("33333"), 3),
("75103401214", "İLAYDA", "KAVMAZ", SHA1("33333"), 3);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES
("33299403474", "BERAY", "KADAK", SHA1("44444"), 4),
("94655202698", "AZİZ", "HELVACIOĞLU", SHA1("44444"), 4);

INSERT INTO departments (name)
VALUES
("Cardiology"),
("Dermatology"),
("General Surgery"),
("Neurology"),
("Radiology");

INSERT INTO doctor_departments (user_id, department_id)
VALUES
("85647403018", 1),
("48679641476", 2),
("21764134480", 3),
("19852145364", 4),
("35787960612", 5);

INSERT INTO rooms (name)
VALUES
("101"),
("102"),
("201"),
("202"),
("301"),
("302"),
("401"),
("402"),
("501"),
("502");

INSERT INTO treatment_types (name)
VALUES
("Outpatient"),
("Inpatient");

INSERT INTO appointments (patient_id, doctor_id, room_id, treatment_type, start_time, end_time)
VALUES
("76280461060", "85647403018", 1, 1, "2019-12-01 13:00:00", "2019-12-01 14:00:00"),
("59962434170", "48679641476", 3, 1, "2019-12-02 12:00:00", "2019-12-01 13:00:00"),
("46820055516", "21764134480", 5, 1, "2019-12-03 11:00:00", "2019-12-01 12:00:00"),
("37266371162", "19852145364", 7, 2, "2019-12-04 09:00:00", "2019-12-07 18:00:00"),
("33537884584", "35787960612", 9, 2, "2019-12-05 09:00:00", "2019-12-06 17:00:00");

INSERT INTO rest_days (start_time, end_time, user_id)
VALUES
("2019-12-30 09:00:00", "2020-01-02 09:00:00", "85647403018"),
("2019-12-30 09:00:00", "2020-01-02 09:00:00", "48679641476"),
("2019-12-30 09:00:00", "2020-01-02 09:00:00", "21764134480"),
("2019-12-30 09:00:00", "2020-01-02 09:00:00", "19852145364"),
("2019-12-30 09:00:00", "2020-01-02 09:00:00", "35787960612");
