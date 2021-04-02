-- Password admin
INSERT INTO users VALUES (1, 'admin@admin.adm', 'Admin', 'Admin', 'admin', '$2a$08$vcoMslAh4djYji8CCVVs4e4hJHw9nFZmtBehGjDoLj7AaGd5OlIAu');
-- Password demo
INSERT INTO users VALUES (2, 'demo@demo.demo', 'Demo', 'Demo', 'demo', '$2a$08$bHQfhH3MFniIqayaJ6TGSetn3QMjctrQvUgXn7/20DnxT.UWY7GuO');

INSERT INTO user_roles VALUES (1, 'USER');
INSERT INTO user_roles VALUES (1, 'ADMIN');
INSERT INTO user_roles VALUES (2, 'USER');

INSERT INTO tasks VALUES (1, '2021-01-30 13:10:02.0474381', '2021-03-30 17:10:02.0474381',
'Amet consectetur adipiscing elit ut aliquam purus. Massa tempor nec feugiat nisl pretium fusce id velit. Id porta nibh venenatis cras sed. Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci a. Viverra suspendisse potenti nullam ac tortor vitae purus. Duis at consectetur lorem donec massa sapien faucibus. Nulla aliquet porttitor lacus luctus accumsan. Tristique senectus et netus et malesuada fames ac turpis egestas. Ipsum nunc aliquet bibendum enim facilisis gravida neque convallis a. Cras fermentum odio eu feugiat pretium nibh ipsum consequat nisl. Odio ut sem nulla pharetra diam sit amet. Suspendisse faucibus interdum posuere lorem ipsum dolor sit amet. Etiam tempor orci eu lobortis elementum nibh tellus molestie nunc. Nec feugiat in fermentum posuere urna nec. Sit amet est placerat in. Praesent semper feugiat nibh sed pulvinar proin gravida hendrerit.',
'OPEN', 'Lorem ipsum dolor sit amet', 2, 1);
INSERT INTO tasks VALUES (2, '2021-01-30 13:10:02.0474381', '2021-03-30 17:10:02.0474381',
'Edit Me!', 'OPEN', 'Demo Task', 2, 2);
INSERT INTO tasks VALUES (3, '2021-01-30 13:10:02.0474381', '2021-03-27 17:10:02.0474381',
'Ознакомление с функциональностью сервиса Kanban Board',
'IN_PROGRESS', 'Изучение возможностей Kanban Board', 2, 1);