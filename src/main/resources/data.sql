INSERT INTO user (id, username, password, role)
VALUES (1, ''root'', ''1234 '', ''admin'');
INSERT INTO user (id, username, password, role)
VALUES (2, ''202312345678 '', ''student123'', ''student'');
INSERT INTO student (id, name, student_number, phone, address, email, user_id)
VALUES (1, ''张三'', ''202312345678 '', ''13800138000 '', ''北京市海淀区'', ''zhangsan@example.com'', 2);