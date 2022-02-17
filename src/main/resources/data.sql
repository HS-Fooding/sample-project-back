INSERT INTO roles (id, role_name) VALUES (1L, 'ROLE_ADMIN'), (2L, 'ROLE_USER');
INSERT INTO users (id, age, email, name, sex, user_id, user_password) VALUES
(3L, 50, 'fooding@naver.com', 'fooding', true, 'fooding', '$2a$10$uZCdK.nO/.6IuybkczdBSe3m2pDGFfJyQnqlu9z4Wt9nWgr5Xarbq'),
(4L, 26, 'jhs@naver.com', 'jhs', true, 'wjdgustmd', '$2a$10$LnwAbgFhXf935bQ9s8pb6.yupc04YRruOAU92amCph.4UPyI49T0i'),
(5L, 26, 'lgi@naver.com', 'lgi', true, 'dlaruddlr', '$2a$10$t8E0P.CwtyhExHVaRb/oeulhPP9UQBVOJ9c.Nhnao3quQ4uYY4Ghm'),
(6L, 25, 'kdy@naver.com', 'kdy', false, 'rlaehdus', '$2a$10$wOHKvXXfr7UDtPpdC19en.1HUN1aSyBaYK7CRoiju2iOPxh5/Wnia'),
(7L, 24, 'cji@naver.com', 'cji', false, 'chlwpdls', '$2a$10$Vf4MHH523xZehweFsyJ4recXnv8rAc9RQVes4L8BDkR1cbt9LpYq.');

insert into user_role(user_id, role_id) values (3L, 1L), (4L, 2L), (5L, 2L), (6L, 2L), (7L, 2L);



