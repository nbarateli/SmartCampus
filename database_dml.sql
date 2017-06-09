USE smartcampus;

INSERT INTO campus_user (first_name, last_name, user_email, user_type, user_role, user_status) VALUES
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

INSERT INTO room (room_name, room_floor, room_type, capacity, seat_type) VALUES
  ('200', 2, 'auditorium', 200, 'DESKS'),
  ('401', 4, 'auditorium', 50, 'DESKS'),
  ('408', 4, 'auditorium', 70, 'DESKS'),
  ('216', 2, 'auditorium', 70, 'DESKS'),
  ('307-1', 3, 'auditorium', 40, 'PLASTIC_CHAIR'),
  ('412-2', 4, 'auditorium', 40, 'PLASTIC_CHAIR'),
  ('420', 4, 'auditorium', 100, 'DESKS'),
  ('309', 3, 'auditorium', 70, 'DESKS'),
  ('410', 4, 'auditorium', 100, 'PLASTIC_CHAIR'),
  ('417', 4, 'auditorium', 70, 'DESKS'),
  ('კაფეტერია', 2, 'utility', 200, 'TABLES'),
  ('402-2', 4, 'auditorium', 40, 'PLASTIC_CHAIR'),
  ('318', 3, 'auditorium', 70, 'DESKS'),
  ('103', 1, 'auditorium', 30, 'DESKS'),
  ('ქვედა სტუდენტური', 0, 'utility', 20, 'WOODEN_CHAIR'),
  ('ზედა სტუდენტური', 1, 'utility', 30, 'WOODEN_CHAIR'),
  ('ბიბლიოთეკა', 1, 'utility', 150, 'TABLES'),
  ('301', 3, 'auditorium', 40, 'COMPUTERS'),
  ('001', 0, 'utility', 30, 'TABLES'),
  ('217', 0, 'auditorium', 70, 'DESKS');

INSERT INTO room_problem (room_id, reported_by, description, date_created) VALUES
  (11, 1, 'ფანჯარა არ იხურება', str_to_date('07-06-2017', '%d-%m-%Y')),
  (6, 3, 'კონდიციონერი არ არის', str_to_date('06-06-2017', '%d-%m-%Y'));

INSERT INTO lecture (lecturer, room_id, subject_id, day_of_week, start_time, end_time) VALUES
  (6, 4, 1, 'monday', str_to_date('10:00', '%H:%i'), str_to_date('12:10', '%H:%i')),
  (6, 4, 1, 'monday', str_to_date('13:30', '%H:%i'), str_to_date('15:40', '%H:%i')),
  (6, 20, 1, 'thursday', str_to_date('10:00', '%H:%i'), str_to_date('12:10', '%H:%i')),
  (6, 5, 1, 'friday', str_to_date('11:10', '%H:%i'), str_to_date('13:20', '%H:%i')),
  (7, 9, 2, 'tuesday', str_to_date('11:10', '%H:%i'), str_to_date('12:10', '%H:%i')),
  (7, 10, 2, 'tuesday', str_to_date('12:20', '%H:%i'), str_to_date('14:30', '%H:%i')),
  (7, 7, 2, 'friday', str_to_date('10:00', '%H:%i'), str_to_date('11:00', '%H:%i')),
  (7, 10, 2, 'friday', str_to_date('11:10', '%H:%i'), str_to_date('12:10', '%H:%i')),
  (7, 8, 2, 'friday', str_to_date('13:30', '%H:%i'), str_to_date('14:30', '%H:%i')),
  (9, 3, 3, 'monday', str_to_date('16:00', '%H:%i'), str_to_date('18:00', '%H:%i')),
  (9, 3, 3, 'sunday', str_to_date('12:00', '%H:%i'), str_to_date('17:00', '%H:%i')),
  (8, 2, 4, 'tuesday', str_to_date('17:00', '%H:%i'), str_to_date('19:10', '%H:%i')),
  (8, 2, 4, 'wednesday', str_to_date('17:00', '%H:%i'), str_to_date('19:10', '%H:%i')),
  (8, 2, 4, 'saturday', str_to_date('11:10', '%H:%i'), str_to_date('13:20', '%H:%i'));
    
    