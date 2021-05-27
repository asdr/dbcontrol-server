-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

--insert into t_user (pk, created_date, unique_id, updated_date, version, email, firstname, lastname, locked, password_encrypted) values (1, sysdate, 'o0ExuMDJImUd5nzVm3R8V', sysdate, 0, 'sg@promising.ai', 'Cesi', 'Ceyms', 'N', '$2a$10$KbM/8zlX/I5moVpOV8OsuuuVxUdNbmQTi4QEdlGldmBXB9okw/c5y');
--insert into t_auth_token (pk, created_date, unique_id, updated_date, version, expiration_date, ip_address, token_value, valid, owner_pk) values (1, sysdate, 'MHMwd58Mj9Qi1kUJJppD5', sysdate, 0, sysdate+7, '127.0.0.1', 'AHT0eMDaOl0Bfc0Y2vfbw|20191119-04:03:43', 'Y', 1);
--insert into t_role (pk, created_date, unique_id, updated_date, version, name) values (1, sysdate, 'ADMINISTRATOR', sysdate, 0, 'ADMINISTRATOR');
--insert into t_user_role (user_pk, role_pk) values (1, 1);
--insert into t_resource (pk, created_date, unique_id, updated_date, version, name, owner_pk) values (1, sysdate, 'UserServiceImpl.getModels', sysdate, 0, 'UserServiceImpl.getModels', 1);
--insert into t_resource_role (resource_pk, role_pk) values (1, 1);
--insert into t_user (pk, created_date, unique_id, updated_date, version, email, firstname, lastname, locked, password_encrypted) values (2, sysdate, '8190281097209172', sysdate, 0, 'dev1@devops.net', 'Dev1', 'Dev1', 'N', '$2a$10$KbM/8zlX/I5moVpOV8OsuuuVxUdNbmQTi4QEdlGldmBXB9okw/c5y');
--insert into t_auth_token (pk, created_date, unique_id, updated_date, version, expiration_date, ip_address, token_value, valid, owner_pk) values (2, sysdate, '6785467865467', sysdate, 0, sysdate+7, '127.0.0.1', 'p7TifntaAfT3COi4n5Fds|20191125-03:26:57', 'Y', 2);
--insert into t_resource (pk, created_date, unique_id, updated_date, version, name, owner_pk) values (2, sysdate, 'UserServiceImpl.getModel', sysdate, 0, 'UserServiceImpl.getModel', 2);
--insert into t_resource_role (resource_pk, role_pk) values (2, 1);
select 1 from dual;
