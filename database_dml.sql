use smartcampus;

INSERT INTO campus_user (first_name, last_name, user_email, user_type, user_role, user_status)
VALUES
  ('nino', 'khaduri', 'nkhad15@freeuni.edu.ge', 'user', 'student', 'active'),
  ('niko', 'barateli', 'nbara15@freeuni.edu.ge', 'admin', 'student', 'active'),
  ('zaur', 'meshveliani', 'zmesh15@freeuni.edu.ge', 'user', 'student', 'active'),
  ('davit', 'maghaltadze', 'dmagh15@freeuni.edu.ge', 'user', 'student', 'banned'),
  ('shota', 'nanushsvili', 'snanu15@freeuni.edu.ge', 'user', 'student', 'active'),
  ('nikoloz', 'matchavariani', 'n.matchavariani@freeuni.edu.ge', 'admin', 'lecturer', 'active'),
  ('nikoloz', 'tsimakuridze', 'n.tsimakuridze@freeuni.edu.ge', 'admin', 'lecturer', 'active'),
  ('lia', 'solomonia', 'l.solomonia@freeuni.edu.ge', 'admin', 'lecturer', 'active'),
  ('giorgi', 'saghinadze', 'g.saghinadze@freeuni.edu.ge', 'admin', 'lecturer', 'active');


INSERT INTO campus_subject (subject_name) VALUES
  ('კალკულუსი II'),
  ('თეორიული ინფორმატიკა'),
  ('გაძლიერებული ალგორითმები I'),
  ('გერმანული II');

INSERT INTO room (room_name, room_floor, room_type, capacity, available_for_students, seat_type) VALUES
  ('200', 2, 'auditorium', 200, FALSE, 'DESKS'),
  ('401', 4, 'auditorium', 50, TRUE, 'DESKS'),
  ('408', 4, 'auditorium', 70, TRUE, 'DESKS'),
  ('216', 2, 'auditorium', 70, TRUE, 'DESKS'),
  ('307-1', 3, 'auditorium', 40, TRUE, 'PLASTIC_CHAIR'),
  ('412-2', 4, 'auditorium', 40, TRUE, 'PLASTIC_CHAIR'),
  ('420', 4, 'auditorium', 100, FALSE, 'DESKS'),
  ('309', 3, 'auditorium', 70, TRUE, 'DESKS'),
  ('410', 4, 'auditorium', 100, TRUE, 'PLASTIC_CHAIR'),
  ('417', 4, 'auditorium', 70, TRUE, 'DESKS'),
  ('კაფეტერია', 2, 'utility', 200, FALSE, 'TABLES'),
  ('402-2', 4, 'auditorium', 40, TRUE, 'PLASTIC_CHAIR'),
  ('318', 3, 'auditorium', 70, TRUE, 'DESKS'),
  ('103', 1, 'auditorium', 30, FALSE, 'DESKS'),
  ('ქვედა სტუდენტური', 0, 'utility', 20, FALSE, 'WOODEN_CHAIR'),
  ('ზედა სტუდენტური', 1, 'utility', 30, FALSE, 'WOODEN_CHAIR'),
  ('ბიბლიოთეკა', 1, 'utility', 150, FALSE, 'TABLES'),
  ('301', 3, 'auditorium', 40, FALSE, 'COMPUTERS'),
  ('001', 0, 'utility', 30, FALSE, 'TABLES'),
  ('217', 0, 'auditorium', 70, TRUE, 'DESKS');

INSERT INTO room_problem (room_id, reported_by, description, date_created) VALUES
  (11, 1, 'ფანჯარა არ იხურება', str_to_date('07-06-2017', '%d-%m-%Y')),
  (6, 3, 'კონდიციონერი არ არის', str_to_date('06-06-2017', '%d-%m-%Y'));

INSERT INTO lecture (lecturer, room_id, subject_id, day_of_week, start_time, end_time) VALUES
  (6, 4, 1, 'monday', '10:00', '12:10'),
  (6, 4, 1, 'monday', '13:30', '15:40'),
  (6, 20, 1, 'thursday', '10:00', '12:10'),
  (6, 5, 1, 'friday', '11:10', '13:20'),
  (7, 9, 2, 'tuesday', '11:10', '12:10'),
  (7, 10, 2, 'tuesday', '12:20', '14:30'),
  (7, 7, 2, 'friday', '10:00',  '11:00'),
  (7, 10, 2, 'friday', '11:10',  '12:10'),
  (7, 8, 2, 'friday', '13:30', '14:30'),
  (9, 3, 3, 'monday', '16:00', '18:00'),
  (9, 3, 3, 'sunday', '12:00', '17:00'),
  (8, 2, 4, 'tuesday', '17:00',  '19:10'),
  (8, 2, 4, 'wednesday', '17:00',  '19:10'),
  (8, 2, 4, 'saturday', '11:10',  '13:20');
  
    
    