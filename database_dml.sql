USE smartcampus;

INSERT INTO campus_user (first_name, last_name, user_email, initial_role)
VALUES
  ('ნინო', 'ხადური', 'nkhad15@freeuni.edu.ge', 'sys_admin'),
  ('ნიკო', 'ბარათელი', 'nbara15@freeuni.edu.ge', 'sys_admin'),
  ('ზაურ', 'მეშველიანი', 'zmesh15@freeuni.edu.ge', 'sys_admin'),
  ('დავით', 'მაღალთაძე', 'dmagh15@freeuni.edu.ge', 'sys_admin'),
  ('შოთა', 'ნანუაშვილი', 'snanu15@freeuni.edu.ge', 'sys_admin'),
  ('ნიკოლოზ', 'მაჭავარიანი', 'n.matchavariani@freeuni.edu.ge', 'lecturer'),
  ('ნიკოლოზ', 'ციმაკურიძე', 'n.tsimakuridze@freeuni.edu.ge', 'lecturer'),
  ('ლია', 'სოლომონია', 'l.solomonia@freeuni.edu.ge', 'lecturer'),
  ('გიორგი', 'საღინაძე', 'g.saghinadze@freeuni.edu.ge', 'lecturer'),
  ('ალექსანდრე', 'ცხოვრებოვი', 'atskh15@freeuni.edu.ge', 'student');


INSERT INTO campus_subject (subject_name) VALUES
  ('კალკულუსი II'),
  ('თეორიული ინფორმატიკა'),
  ('გაძლიერებული ალგორითმები I'),
  ('გერმანული II'),
  ('წყლის ნაყვის პატერნები');


INSERT INTO item_report (item_name, item_description, author_id, report_type, date_added) VALUES
  ('ტანსაცმლის კაჟადა', 'ყავისფერი, ხის, პატარა', 2, 'found', '09-05-17'),
  ('უცხო ქვეყნის დროშა', 'დიდი, ლამაზი გაშლილი', 1, 'lost', '11-04-17'),
  ('სათვალე', 'მზის ყავისფერი სათვალე', 5, 'lost', '2-06-17'),
  ('პასტა', '0.3მმ, შავი ციმაკურიძის პასტა', 3, 'lost', '21-02-17'),
  ('ყურსასმენი', 'თეთრი', 3, 'found', '16-05-17'),
  ('ქუდი', 'ლურჯი, ყურებიანი', 3, 'lost', '20-03-17'),
  ('რვეული', 'სახატავი რვეული', 3, 'lost', '19-02-17'),
  ('ლეპტოპი', 'alienware Intel® Core™ i7-7820HK', 3, 'found', '18-04-17'),
  ('საფულე', 'შავი, ტყავის', 6, 'lost', '17-05-17'),
  ('ყურსასმენი', 'მწვანე, Panasonic-ის', 3, 'found', '16-05-17');

