DROP DATABASE IF EXISTS SmartCampus;
CREATE DATABASE SmartCampus;
USE SmartCampus;

CREATE TABLE role (
  role_id INT NOT NULL AUTO_INCREMENT,
  role_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE INDEX role_name_UNIQUE (role_name ASC));
  
CREATE TABLE campus_user
(
  user_id     INT PRIMARY KEY        NOT NULL AUTO_INCREMENT,
  first_name  VARCHAR(20)            NOT NULL,
  last_name   VARCHAR(20)            NOT NULL,
  user_email  VARCHAR(30)            NOT NULL,
  user_type   ENUM ('user', 'admin') NOT NULL,
  user_role   INT,
  user_status ENUM ('active', 'banned'),
  img_url     VARCHAR(300),
  CONSTRAINT user_role_fk
  FOREIGN KEY (user_role) REFERENCES role (role_id)
    ON DELETE SET NULL
);
CREATE UNIQUE INDEX user_user_email_uindex
  ON campus_user (user_email);

CREATE TABLE room
(
  room_id                INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_name              VARCHAR(40)     NOT NULL,
  room_floor             INT             NOT NULL,
  room_type              ENUM ('auditorium', 'utility'),
  capacity               INT,
  available_for_students BOOLEAN         NOT NULL,
  seat_type              ENUM ('DESKS', 'WOODEN_CHAIR', 'PLASTIC_CHAIR', 'COMPUTERS', 'TABLES')
);

CREATE TABLE room_problem
(
  problem_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_id      INT             NOT NULL,
  reported_by  INT             NOT NULL,
  solved_by    INT,
  title        VARCHAR(100),
  description  VARCHAR(500),
  date_created DATE            NOT NULL,
  CONSTRAINT room_id_fk
  FOREIGN KEY (room_id) REFERENCES room (room_id)
    ON DELETE CASCADE,
  CONSTRAINT reporter_id_fk
  FOREIGN KEY (reported_by) REFERENCES campus_user (user_id)
    ON DELETE CASCADE,
  CONSTRAINT solver_id_fk
  FOREIGN KEY (solved_by) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);

CREATE TABLE booking
(
  booking_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_id    INT             NOT NULL,
  booker_id  INT             NOT NULL,
  start_date DATE            NOT NULL,
  CONSTRAINT booking_room_fk
  FOREIGN KEY (room_id) REFERENCES room (room_id)
    ON DELETE CASCADE,
  CONSTRAINT booking_user_fk
  FOREIGN KEY (booker_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE

);

CREATE TABLE corridor_problem
(
  problem_id  INT AUTO_INCREMENT
    PRIMARY KEY,
  reported_by INT          NOT NULL,
  solved_by   INT,
  floor       INT          NOT NULL,
  title       VARCHAR(100) NOT NULL,
  description VARCHAR(500) NOT NULL,
  CONSTRAINT reported_user_fk
  FOREIGN KEY (reported_by) REFERENCES campus_user (user_id)
    ON DELETE CASCADE,
  CONSTRAINT solved_by_fk
  FOREIGN KEY (solved_by) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);

CREATE TABLE campus_subject
(
  subject_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  subject_name VARCHAR(100)    NOT NULL
);

CREATE TABLE lecture
(
  lecture_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  lecturer    INT             NOT NULL,
  room_id     INT             NOT NULL,
  subject_id  INT             NOT NULL,
  day_of_week ENUM ('monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'),
  start_time  TIME            NOT NULL,
  end_time    TIME            NOT NULL,
  CONSTRAINT lecture_user_user_id_fk FOREIGN KEY (lecturer) REFERENCES campus_user (user_id)
    ON DELETE CASCADE,
  CONSTRAINT lecture_room_room_id_fk FOREIGN KEY (room_id) REFERENCES room (room_id)
    ON DELETE CASCADE,
  CONSTRAINT lecture_subject_subject_id_fk FOREIGN KEY (subject_id) REFERENCES campus_subject (subject_id)
    ON DELETE CASCADE
);

CREATE TABLE item_report
(
  report_id        INT AUTO_INCREMENT
    PRIMARY KEY,
  item_name        VARCHAR(50)            NULL,
  item_description VARCHAR(500)           NOT NULL,
  author_id        INT                    NOT NULL,
  report_type      ENUM ('lost', 'found') NOT NULL,
  date_added       DATE                   NOT NULL,
  CONSTRAINT item_report_user_user_id_fk
  FOREIGN KEY (author_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);

CREATE TABLE item_image
(
  image_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  image_url VARCHAR(300)    NOT NULL,
  report_id INT             NOT NULL,
  CONSTRAINT item_image_item_report_report_id_fk FOREIGN KEY (report_id) REFERENCES item_report (report_id)
    ON DELETE CASCADE
);
CREATE UNIQUE INDEX item_image_image_url_uindex
  ON item_image (image_url);

CREATE TABLE user_problem
(
  problem_id      INT AUTO_INCREMENT
    PRIMARY KEY,
  user_id         INT          NOT NULL,
  warner_id       INT          NOT NULL,
  warning_title   VARCHAR(100) NOT NULL,
  warning_message VARCHAR(500) NOT NULL,
  date_warned     DATE         NOT NULL,
  CONSTRAINT user_problem_user_user_id_fk
  FOREIGN KEY (user_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE,
  CONSTRAINT user_problem_user_warner_id_fk
  FOREIGN KEY (warner_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);

CREATE TABLE room_image
(
  image_id  INT          NOT NULL AUTO_INCREMENT,
  image_url VARCHAR(300) NOT NULL,
  room_id   INT          NOT NULL,
  PRIMARY KEY (image_id),
  INDEX fk_room_images_rooms_idx (room_id ASC),
  CONSTRAINT fk_room_images_rooms
  FOREIGN KEY (room_id)
  REFERENCES room (room_id)
    ON DELETE CASCADE
);

CREATE TABLE permission (
  permission_id INT NOT NULL AUTO_INCREMENT,
  permission_description VARCHAR(200) NOT NULL,
  PRIMARY KEY (permission_id),
  UNIQUE INDEX permission_description_UNIQUE (permission_description ASC));

CREATE TABLE role_permission (
  id INT NOT NULL AUTO_INCREMENT,
  role_id INT NOT NULL,
  permission_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_role_permission_role_idx (role_id ASC),
  INDEX fk_role_permission_permission_idx (permission_id ASC),
  CONSTRAINT fk_role_permission_role
    FOREIGN KEY (role_id)
    REFERENCES smartcampus.role (role_id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_role_permission_permission
    FOREIGN KEY (permission_id)
    REFERENCES smartcampus.permission (permission_id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
