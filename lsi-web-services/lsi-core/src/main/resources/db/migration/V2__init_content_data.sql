/*INSERT GROUP*/
insert into sys_parameter_group (id, code, name, status) VALUES (1, 'GRP001', 'Validity Status', 1);
insert into sys_parameter_group (id, code, name, status) VALUES (2, 'GRP002', 'Completion Status', 1);
insert into sys_parameter_group (id, code, name, status) VALUES (3, 'GRP003', 'Question Type', 1);
insert into sys_parameter_group (id, code, name, status) VALUES (4, 'GRP004', 'Component Type', 1);

/*INSERT PARAM VALIDITY*/
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (1, 'VS0001', 'NOT VALID', 1, 1);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (2, 'VS0002', 'LESS VALID', 1, 1);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (3, 'VS0003', 'VALID ENOUGH', 1, 1);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (4, 'VS0004', 'VALID', 1, 1);

/*INSERT PARAM COMPLETION*/
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (5, 'IS0001', 'COMPLETE', 1, 2);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (6, 'IS0002', 'NOT COMPLETE', 1, 2);

/*INSERT PARAM QUESTION*/
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (7, 'QT0001', 'MULTIPLE CHOICE', 1, 3);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (8, 'QT0002', 'ESSAY', 1, 3);

/*INSERT PARAM COMPONENT*/
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (9, 'CT0001', 'QUESTION', 1, 4);
insert into sys_parameter (id, code, name, status, parameter_group_id) VALUES (10, 'CT0002', 'STATEMENT', 1, 4);