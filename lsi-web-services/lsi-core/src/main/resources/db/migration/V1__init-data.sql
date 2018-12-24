CREATE TABLE sys_parameter_group (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(150) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(150) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE sys_parameter (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(150) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(150) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  parameter_group_id BIGINT(20) DEFAULT NULL ,
  KEY fk_group_parameter (parameter_group_id) USING BTREE,
  CONSTRAINT cs_parameter_group_parameter FOREIGN KEY (parameter_group_id) REFERENCES sys_parameter_group (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE trx_interview_event (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  event_start_date TIMESTAMP NULL DEFAULT NULL ,
  event_end_date TIMESTAMP NULL DEFAULT NULL ,
  PRIMARY KEY (id)
);

CREATE TABLE trx_event_candidate (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  candicate_image_path VARCHAR(256) DEFAULT NULL,
  interview_event_id BIGINT(20) DEFAULT NULL ,
  candidate_order_number INTEGER DEFAULT NULL ,
  KEY fk_interview_candidate_event (interview_event_id) USING BTREE,
  CONSTRAINT cs_interview_candidate_event FOREIGN KEY (interview_event_id) REFERENCES trx_interview_event (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE trx_interview_header (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  user_id BIGINT(20) DEFAULT NULL ,
  province_id BIGINT(20) DEFAULT NULL ,
  city_id BIGINT(20) DEFAULT NULL ,
  district_id BIGINT(20) DEFAULT NULL ,
  village_id BIGINT(20) DEFAULT NULL ,
  branch_id BIGINT(20) DEFAULT NULL ,
  number_of_respondent INTEGER DEFAULT NULL ,
  interview_event_id BIGINT(20) DEFAULT NULL ,
  PRIMARY KEY (id),
  KEY fk_interview_header_event (interview_event_id) USING BTREE,
  CONSTRAINT cs_interview_header_event FOREIGN KEY (interview_event_id) REFERENCES trx_interview_event (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE mst_interview_component (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  component_type VARCHAR(6) DEFAULT NULL ,
  component_order INTEGER DEFAULT NULL ,
  interview_event_id BIGINT(20) DEFAULT NULL,
  KEY fk_interview_component_interview_event (interview_event_id) USING BTREE,
  CONSTRAINT cs_interview_component_interview_event FOREIGN KEY (interview_event_id) REFERENCES trx_interview_event (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE mst_statement_image (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  interview_component_id BIGINT(20) DEFAULT NULL,
  KEY fk_interview_component (interview_component_id) USING BTREE,
  CONSTRAINT cs_statement_image_interview_component FOREIGN KEY (interview_component_id) REFERENCES mst_interview_component (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE mst_question (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  interview_component_id BIGINT(20) DEFAULT NULL,
  question_type VARCHAR(6) DEFAULT NULL ,
  KEY fk_interview_question_interview_component (interview_component_id) USING BTREE,
  CONSTRAINT cs_interview_question_interview_component FOREIGN KEY (interview_component_id) REFERENCES mst_interview_component (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);


CREATE TABLE mst_question_answer (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  question_id BIGINT(20) DEFAULT NULL,
  KEY fk_question_answer_interview_question (question_id) USING BTREE,
  CONSTRAINT cs_question_answer_interview_question FOREIGN KEY (question_id) REFERENCES mst_question (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);


CREATE TABLE trx_interview_detail (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  latitude DOUBLE(10, 2) DEFAULT NULL ,
  longitude DOUBLE(10, 2) DEFAULT NULL ,
  interview_status VARCHAR(6) DEFAULT NULL ,
  respondent_name VARCHAR(150) DEFAULT NULL ,
  respondent_age INTEGER DEFAULT NULL ,
  respondent_gender TINYINT(1) DEFAULT NULL ,
  respondent_religion VARCHAR(6) DEFAULT NULL ,
  interview_start_time TIMESTAMP NULL DEFAULT NULL ,
  interview_end_time TIMESTAMP NULL DEFAULT NULL ,
  interview_header_id BIGINT(20) DEFAULT NULL,
  validity_status VARCHAR(6) DEFAULT NULL,
  event_candidate_before BIGINT(20) DEFAULT NULL ,
  event_candidate_after BIGINT(20) DEFAULT NULL ,
  interview_score INTEGER DEFAULT NULL ,
  KEY fk_interview_header (interview_header_id) USING BTREE,
  CONSTRAINT cs_interview_detail_header FOREIGN KEY (interview_header_id) REFERENCES trx_interview_header (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE trx_interview_image (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  interview_detail_id BIGINT(20) DEFAULT NULL,
  upload_time_stamp DATETIME NULL DEFAULT NULL ,
  file_path VARCHAR(255) DEFAULT NULL ,
  file_size BIGINT(20) DEFAULT NULL ,
  file_type VARCHAR (150) DEFAULT NULL ,
  latitude DOUBLE(10, 2) DEFAULT NULL ,
  longitude DOUBLE(10, 2) DEFAULT NULL ,
  KEY fk_interview_image_header (interview_detail_id) USING BTREE,
  CONSTRAINT cs_interview_image_detail FOREIGN KEY (interview_detail_id) REFERENCES trx_interview_detail (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE trx_interview_user_answer (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) DEFAULT NULL UNIQUE,
  name VARCHAR(150) DEFAULT NULL,
  status TINYINT(1) DEFAULT NULL,
  remarks VARCHAR(255) DEFAULT NULL,
  created_by VARCHAR(50) DEFAULT NULL,
  created_on TIMESTAMP NULL DEFAULT NULL,
  modified_by VARCHAR(50) DEFAULT NULL,
  modified_on TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  interview_detail_id BIGINT(20) DEFAULT NULL,
  interview_question_id BIGINT(20) DEFAULT NULL ,
  question_answer_id BIGINT(20) DEFAULT NULL ,
  other_answer VARCHAR(256) DEFAULT NULL ,
  KEY fk_interview_user_answer_interview_detail (interview_detail_id) USING BTREE,
  CONSTRAINT cs_interview_user_answer_interview_detail FOREIGN KEY (interview_detail_id) REFERENCES trx_interview_detail (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (id)
);
