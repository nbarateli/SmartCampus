DROP DATABASE IF EXISTS SmartCampus;
CREATE DATABASE SmartCampus;
USE SmartCampus;

CREATE TABLE campus_user
(
  user_id      INT PRIMARY KEY                                             NOT NULL AUTO_INCREMENT,
  first_name   VARCHAR(20)                                                 NOT NULL,
  last_name    VARCHAR(20)                                                 NOT NULL,
  user_email   VARCHAR(30)                                                 NOT NULL,
  initial_role ENUM ('student', 'lecturer', 'staff', 'admin', 'sys_admin') NOT NULL,
  img_url      VARCHAR(300)
);
CREATE UNIQUE INDEX user_user_email_uindex
  ON campus_user (user_email);

CREATE TABLE room
(
  room_id                INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_name              VARCHAR(40)     NOT NULL,
  room_floor             INT             NOT NULL,
  room_type              ENUM ('auditorium', 'utility', 'laboratory'),
  capacity               INT,
  available_for_students BOOLEAN         NOT NULL,
  seat_type              ENUM ('DESKS', 'CHAIRS', 'COMPUTERS', 'TABLES')
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

CREATE TABLE campus_subject
(
  subject_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  subject_name VARCHAR(100)    NOT NULL
);

CREATE TABLE booking
(
  booking_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_id      INT             NOT NULL,
  booker_id    INT             NOT NULL,
  booking_date DATE            NOT NULL,
  subject_id   INT             NULL,
  description  VARCHAR(100)    NULL     DEFAULT NULL,
  start_time   TIME            NOT NULL,
  end_time     TIME            NOT NULL,
  CONSTRAINT booking_subject_fk
  FOREIGN KEY (subject_id)
  REFERENCES smartcampus.campus_subject (subject_id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
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

/**
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
); */


CREATE TABLE item_report
(
  report_id        INT AUTO_INCREMENT
    PRIMARY KEY,
  item_name        VARCHAR(50)            NULL,
  item_description VARCHAR(500)           NOT NULL,
  author_id        INT                    NOT NULL,
  report_type      ENUM ('lost', 'FOUND') NOT NULL,
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

CREATE TABLE user_warning
(
  warning_id      INT AUTO_INCREMENT
    PRIMARY KEY,
  user_id         INT          NOT NULL,
  warner_id       INT          NOT NULL,
  warning_title   VARCHAR(100) NOT NULL,
  warning_message VARCHAR(500) NOT NULL,
  date_warned     DATE         NOT NULL,
  CONSTRAINT user_warning_user_user_id_fk
  FOREIGN KEY (user_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE,
  CONSTRAINT user_warning_user_warner_id_fk
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

CREATE TABLE smartcampus.user_role
(
  user_id INT                                                         NOT NULL,
  role    ENUM ('student', 'lecturer', 'staff', 'admin', 'sys_admin') NOT NULL,
  CONSTRAINT user_role_campus_user_user_id_fk FOREIGN KEY (user_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);
CREATE TABLE smartcampus.user_permission
(
  user_id    INT NOT NULL,
  permission ENUM
             ('book_a_room', 'request_booked_room', 'cancel_booking', 'report_room_problem',
                             'delete_problem', 'lost_found_post', 'lost_found_delete', 'warn_user',
                             'view_user_warnings', 'delete_user_warnings', 'remove_permission', 'insert_data')
                 NOT NULL,
  CONSTRAINT user_permission_campus_user_user_id_fk FOREIGN KEY (user_id) REFERENCES campus_user (user_id)
    ON DELETE CASCADE
);

DELIMITER $$

CREATE TRIGGER user_role_trigger
AFTER INSERT ON campus_user
FOR EACH ROW
  BEGIN
    INSERT INTO user_role (user_id, role) VALUES (NEW.user_id, NEW.initial_role);
  END;

DELIMITER ;

DELIMITER //
CREATE TRIGGER user_permission_trigger
AFTER INSERT ON user_role
FOR EACH ROW
  BEGIN
    IF NEW.role = ('student')
    THEN
      BEGIN
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'book_a_room');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'report_room_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_post');
      END;
    ELSE IF
    NEW.role = ('lecturer')
    THEN
      BEGIN
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'book_a_room');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'report_room_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_post');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'request_booked_room');
      END;
    ELSE IF
    NEW.role = ('staff')
    THEN
      BEGIN
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'book_a_room');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'report_room_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_post');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'request_booked_room');
      END;
    ELSE IF
    NEW.role = ('admin')
    THEN
      BEGIN
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'book_a_room');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'report_room_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'delete_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_post');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'cancel_booking');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_delete');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'warn_user');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'view_user_warnings');
      END;
    ELSE IF
    NEW.role = ('sys_admin')
    THEN
      BEGIN
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'book_a_room');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'report_room_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'delete_problem');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_post');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'cancel_booking');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'lost_found_delete');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'warn_user');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'view_user_warnings');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'delete_user_warnings');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'remove_permission');
        INSERT INTO user_permission (user_id, permission) VALUE (NEW.user_id, 'insert_data');
      END;
    END IF;
    END IF;
    END IF;
    END IF;
    END IF;
  END;
DELIMITER ;
