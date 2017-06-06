USE smartcampus;

CREATE TABLE smartcampus.user
(
  user_id     INT PRIMARY KEY        NOT NULL AUTO_INCREMENT,
  first_name  VARCHAR(20)            NOT NULL,
  last_name   VARCHAR(20)            NOT NULL,
  user_email  VARCHAR(30)            NOT NULL,
  user_type   ENUM ('user', 'admin') NOT NULL,
  user_role   ENUM ('student', 'lecturer', 'staff'),
  user_status ENUM ('active', 'banned')
);
CREATE UNIQUE INDEX user_user_email_uindex
  ON smartcampus.user (user_email);
CREATE TABLE smartCampus.room
(
  room_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_name VARCHAR(40)     NOT NULL,
  room_type ENUM ('auditorium', 'utility'),
  capacity  INT,
  seat_type ENUM ('DESKS', 'WOODEN_CHAIR', 'PLASTIC_CHAIR')
);
CREATE TABLE smartCampus.room_problem
(
  problem_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_id      INT             NOT NULL,
  title        VARCHAR(100),
  description  VARCHAR(500),
  date_created DATE            NOT NULL,
  FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE smartcampus.booking
(
  booking_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  room_id    INT             NOT NULL,
  booker_id  INT             NOT NULL,
  start_date DATE            NOT NULL,
  CONSTRAINT booking_room_fk
  FOREIGN KEY (room_id) REFERENCES room (room_id),
  CONSTRAINT booking_user_fk
  FOREIGN KEY (booker_id) REFERENCES user (user_id)

);
CREATE TABLE corridor_problem
(
  problem_id  INT AUTO_INCREMENT
    PRIMARY KEY,
  floor       INT          NOT NULL,
  title       VARCHAR(100) NOT NULL,
  description VARCHAR(500) NOT NULL
);
CREATE TABLE smartcampus.subject (
  subject_id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  subject_name VARCHAR(100)    NOT NULL
);
CREATE TABLE smartcampus.lecture
(
  lecture_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  lecturer    INT             NOT NULL,
  room_id     INT             NOT NULL,
  subject_id  INT             NOT NULL,
  day_of_week ENUM ('monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'),
  start_time  DATE,
  end_time    DATE            NOT NULL,
  CONSTRAINT lecture_user_user_id_fk FOREIGN KEY (lecturer) REFERENCES user (user_id),
  CONSTRAINT lecture_room_room_id_fk FOREIGN KEY (room_id) REFERENCES room (room_id),
  CONSTRAINT lecture_subject_subject_id_fk FOREIGN KEY (subject_id) REFERENCES subject (subject_id)
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
  FOREIGN KEY (report_id) REFERENCES smartcampus.user (user_id)
);

CREATE TABLE smartcampus.item_image
(
  image_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  image_url VARCHAR(300)    NOT NULL,
  report_id INT             NOT NULL,
  CONSTRAINT item_image_item_report_report_id_fk FOREIGN KEY (report_id) REFERENCES item_report (report_id)
);
CREATE UNIQUE INDEX item_image_image_url_uindex
  ON smartcampus.item_image (image_url);
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
  FOREIGN KEY (user_id) REFERENCES smartcampus.user (user_id),
  CONSTRAINT user_problem_user_warner_id_fk
  FOREIGN KEY (warner_id) REFERENCES smartcampus.user (user_id)
);
