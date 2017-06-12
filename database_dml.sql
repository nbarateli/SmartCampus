use smartcampus;

INSERT INTO campus_user (first_name, last_name, user_email, user_type, user_role, user_status)
VALUES
  ('nino', 'khaduri', 'nkhad15@freeuni.edu.ge', 'user', 'student', 'active'),
  ('niko', 'barateli', 'nbara15@freeuni.edu.ge', 'admin', 'student', 'active'),
  ('zaur', 'meshveliani', 'zmesh15@freeuni.edu.ge', 'user', 'student', 'active'),
  ('davit', 'maghaltadze', 'dmagh15@freeuni.edu.ge', 'user', 'student', 'active'),
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
    
INSERT INTO item_report(item_name, item_description , author_id, report_type, date_added) VALUES
  ('ტანსაცმლის კაჟადა', 'ყავისფერი, ხის, პატარა', 4, 'found', '09-05-17'), 
  ('უცხო ქვეყნის დროშა', 'დიდი, ლამაზი გაშლილი', 1, 'lost', '11-04-17'),
  ('სათვალე', 'მზის ყავისფერი სათვალე', 5, 'lost', '2-06-17'),
  ('პასტა', '0.3მმ, შავი ციმაკურიძის პასტა', 3, 'lost', '21-02-17'),
  ('ყურსასმენი', 'თეთრი', 3, 'found', '16-05-17'),
  ('ქუდი', 'ლურჯი, ყურებიანი', 3, 'lost', '20-03-17'),
  ('რვეული', 'სახატავი რვეული', 3, 'lost', '19-02-17'),
  ('ლეპტოპი', 'alienware Intel® Core™ i7-7820HK', 3, 'found', '18-04-17'),
  ('საფულე', 'შავი, ტყავის', 6, 'lost', '17-05-17'),
  ('ყურსასმენი', 'მწვანე, Panasonic-ის', 3, 'found', '16-05-17');

  
INSERT INTO item_image(image_url, report_id) VALUES
  ('wardrobe.jpg', 1), 
  ('flag.jpg', 2), 
  ('sunglasses.jpg', 3), 
  ('pen.jpg', 4),
  ('headphones.jpg', 5),
  ('notebook1.jpg', 6),
  ('notebook2.jpg', 6),
  ('notebook3.jpg', 6),
  ('latpop1.jpg', 7),
  ('laptop2.jpg', 7),
  ('hat.jpg', 8),
  ('wallet.jpg', 9),
  ('headphones5.jpg', 10);
  
  INSERT INTO room_image(image_url, room_id) VALUES
  ('200.jpg', 1), 
  ('401.jpg', 2), 
  ('216.jpg', 3), 
  ('307-1.jpg', 4),
  ('412-2.jpg', 5),
  ('420.jpg', 6),
  ('309.jpg', 7),
  ('410.jpg', 8),
  ('417.jpg', 9),
  ('cafeteria.jpg', 10),
  ('402-2.jpg', 11),
  ('318.jpg', 12),
  ('103.jpg', 13);
  
  
  
  
  