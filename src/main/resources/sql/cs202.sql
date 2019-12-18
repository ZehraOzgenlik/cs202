DROP DATABASE IF EXISTS cs202;
CREATE DATABASE cs202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE CS202;

#DDL

DROP TABLE IF EXISTS doctor_departments;
DROP TABLE IF EXISTS rest_days;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         VARCHAR(11)  NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    user_type  INT,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE departments
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE doctor_departments
(
    doctor_id     VARCHAR(11) NOT NULL,
    department_id INT         NOT NULL,
    PRIMARY KEY (doctor_id),
    FOREIGN KEY (doctor_id) REFERENCES users (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE rooms
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE appointments
(
    id             INT         NOT NULL AUTO_INCREMENT,
    patient_id     VARCHAR(11) NOT NULL,
    doctor_id      VARCHAR(11) NOT NULL,
    room_id        INT         NOT NULL,
    treatment_type INT,
    start_time     DATETIME    NOT NULL,
    end_time       DATETIME    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (doctor_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE rest_days
(
    id         INT         NOT NULL AUTO_INCREMENT,
    start_time DATETIME,
    end_time   DATETIME,
    user_id    VARCHAR(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

#DML

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES ("76280461060", "Si̇dar", "Yildirim", SHA1("11111"), 1),
       ("59962434170", "Adi̇l", "Bi̇li̇can", SHA1("11111"), 1),
       ("46820055516", "Ilgin", "Toptaş", SHA1("11111"), 1),
       ("37266371162", "Cansunur", "Özgür", SHA1("11111"), 1),
       ("33537884584", "Seyfi̇", "İbrahi̇mgi̇l", SHA1("11111"), 1);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES ("85647403018", "Ni̇lsu", "Aydoğan", SHA1("22222"), 2),
       ("48679641476", "Eri̇m", "Karagöl", SHA1("22222"), 2),
       ("21764134480", "Berfu", "Güder", SHA1("22222"), 2),
       ("19852145364", "Aki̇f", "Karpuz", SHA1("22222"), 2),
       ("35787960612", "Bedreddi̇n", "Sezgi̇ner", SHA1("22222"), 2);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES ("47073346598", "Eli̇f", "Akyüz", SHA1("33333"), 3),
       ("65839119170", "Tarkan", "Uzunçakmak", SHA1("33333"), 3),
       ("27835914784", "Eli̇fcan", "Kurtuluş", SHA1("33333"), 3),
       ("25268907762", "Yusufcan", "Evmez", SHA1("33333"), 3),
       ("75103401214", "İlayda", "Kavmaz", SHA1("33333"), 3);

INSERT INTO users (id, first_name, last_name, password, user_type)
VALUES ("33299403474", "Beray", "Kadak", SHA1("44444"), 4),
       ("94655202698", "Azi̇z", "Helvacioğlu", SHA1("44444"), 4);

INSERT INTO departments (name)
VALUES ("Cardiology"),
       ("Dermatology"),
       ("General Surgery"),
       ("Neurology"),
       ("Radiology");

INSERT INTO doctor_departments (doctor_id, department_id)
VALUES ("85647403018", 1),
       ("48679641476", 2),
       ("21764134480", 3),
       ("19852145364", 4),
       ("35787960612", 5);

INSERT INTO rooms (name)
VALUES ("101"),
       ("102"),
       ("201"),
       ("202"),
       ("301"),
       ("302"),
       ("401"),
       ("402"),
       ("501"),
       ("502");

INSERT INTO appointments (patient_id, doctor_id, room_id, treatment_type, start_time, end_time)
VALUES ("76280461060", "85647403018", 1, 1, "2019-12-21 13:00:00", "2019-12-21 14:00:00"),
       ("59962434170", "48679641476", 2, 1, "2019-12-22 12:00:00", "2019-12-22 13:00:00"),
       ("46820055516", "21764134480", 3, 1, "2019-12-23 11:00:00", "2019-12-23 12:00:00"),
       ("37266371162", "19852145364", 4, 2, "2019-12-24 09:00:00", "2019-12-24 18:00:00"),
       ("33537884584", "35787960612", 5, 1, "2019-12-25 09:00:00", "2019-12-25 17:00:00"),
       ("33537884584", "48679641476", 6, 2, "2019-12-26 13:00:00", "2019-12-25 17:00:00"),
       ("33537884584", "35787960612", 7, 1, "2019-12-27 12:00:00", "2019-12-27 17:00:00"),
       ("33537884584", "21764134480", 8, 2, "2019-12-28 11:00:00", "2019-12-28 17:00:00"),
       ("33537884584", "19852145364", 9, 1, "2019-12-29 09:00:00", "2019-12-29 17:00:00"),
       ("33537884584", "35787960612", 5, 2, "2019-12-30 10:00:00", "2019-12-30 17:00:00"),
       ("33537884584", "48679641476", 9, 1, "2019-12-31 14:00:00", "2019-12-31 17:00:00"),
       ("33537884584", "21764134480", 8, 2, "2020-01-01 16:00:00", "2020-01-01 17:00:00"),
       ("33537884584", "85647403018", 7, 1, "2020-01-02 13:00:00", "2020-01-02 17:00:00"),
       ("33537884584", "35787960612", 6, 2, "2020-01-03 12:00:00", "2020-01-03 17:00:00"),
       ("33537884584", "19852145364", 5, 1, "2020-01-04 11:00:00", "2020-01-04 17:00:00"),
       ("33537884584", "35787960612", 4, 2, "2020-01-05 09:00:00", "2020-01-05 17:00:00"),
       ("33537884584", "85647403018", 3, 1, "2020-01-06 09:00:00", "2020-01-06 17:00:00"),
       ("33537884584", "35787960612", 2, 2, "2020-01-07 13:00:00", "2020-01-07 17:00:00"),
       ("33537884584", "19852145364", 1, 1, "2020-01-08 12:00:00", "2020-01-08 17:00:00");

INSERT INTO rest_days (start_time, end_time, user_id)
VALUES ("2019-12-21 09:00:00", "2019-12-21 12:00:00", "85647403018"),
       ("2019-12-22 09:00:00", "2019-12-22 11:00:00", "48679641476"),
       ("2019-12-23 13:00:00", "2019-12-23 18:00:00", "21764134480"),
       ("2019-12-24 18:00:00", "2019-12-24 23:00:00", "19852145364"),
       ("2019-12-25 17:00:00", "2019-12-25 22:00:00", "35787960612"),
       ("2019-12-26 17:00:00", "2019-12-25 22:00:00", "48679641476"),
       ("2019-12-27 17:00:00", "2019-12-27 22:00:00", "35787960612"),
       ("2019-12-28 18:00:00", "2019-12-28 23:00:00", "21764134480"),
       ("2019-12-29 18:00:00", "2019-12-29 23:00:00", "19852145364"),
       ("2019-12-30 18:00:00", "2019-12-30 23:00:00", "35787960612"),
       ("2019-12-31 18:00:00", "2019-12-31 23:00:00", "48679641476"),
       ("2020-01-01 18:00:00", "2020-01-01 23:00:00", "21764134480"),
       ("2020-01-02 18:00:00", "2020-01-02 23:00:00", "85647403018"),
       ("2020-01-03 18:00:00", "2020-01-03 23:00:00", "35787960612"),
       ("2020-01-04 18:00:00", "2020-01-04 23:00:00", "19852145364"),
       ("2020-01-05 18:00:00", "2020-01-05 23:00:00", "35787960612"),
       ("2020-01-06 18:00:00", "2020-01-06 23:00:00", "85647403018"),
       ("2020-01-07 18:00:00", "2020-01-07 23:00:00", "35787960612"),
       ("2020-01-08 18:00:00", "2020-01-08 23:00:00", "19852145364");
