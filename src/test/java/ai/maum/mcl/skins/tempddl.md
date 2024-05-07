<!-- 1. proai ddl

-- proai.api_users definition

-- Drop table

-- DROP TABLE proai.api_users;

CREATE TABLE proai.api_users (
	vendor_id varchar(50) NOT NULL,
	api_key varchar(50) NOT NULL,
	use_yn varchar(1) NULL,
	reg_id varchar(50) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT api_users_unique UNIQUE (vendor_id)
);


-- proai.chat_monitor_log definition

-- Drop table

-- DROP TABLE proai.chat_monitor_log;

CREATE TABLE proai.chat_monitor_log (
	id bigserial NOT NULL,
	room_id int8 DEFAULT 0 NOT NULL,
	seq int8 NULL,
	log text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT chat_monitor_log_pkey PRIMARY KEY (id)
);
CREATE INDEX chat_monitor_log_room_id_idx ON proai.chat_monitor_log USING btree (room_id, seq);


-- proai.chatbot definition

-- Drop table

-- DROP TABLE proai.chatbot;

CREATE TABLE proai.chatbot (
	user_id varchar(30) NOT NULL,
	id bigserial NOT NULL,
	"name" varchar(100) NOT NULL,
	chatbot_type_cd varchar(10) DEFAULT 'RAG'::character varying NULL,
	prompt_role text NULL,
	prompt_requirement text NULL,
	retriever_engine_id int8 NULL,
	llm_engine_id int8 NULL,
	rag_parameters text NULL,
	llm_parameters text NULL,
	questions text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	prompt_tail text NULL,
	tail_engine_id int8 NULL,
	tail_parameters text NULL,
	client_info text NULL,
	multi_turn int4 NULL,
	CONSTRAINT chatbot_pkey PRIMARY KEY (id)
);
CREATE INDEX chatbot_mod_dt_idx ON proai.chatbot USING btree (updated_at);
CREATE INDEX chatbot_reg_dt_idx ON proai.chatbot USING btree (created_at);
CREATE INDEX chatbot_user_id_idx ON proai.chatbot USING btree (user_id, id);


-- proai.chatbot_contents definition

-- Drop table

-- DROP TABLE proai.chatbot_contents;

CREATE TABLE proai.chatbot_contents (
	id bigserial NOT NULL,
	chatbot_id int8 DEFAULT 0 NOT NULL,
	type_cd varchar(10) NOT NULL,
	seq int4 NULL,
	img varchar(200) NULL,
	title varchar(500) NULL,
	"text" varchar(1000) NULL,
	reg_user_id varchar(30) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT chatbot_contents_pkey PRIMARY KEY (id)
);
CREATE INDEX contents_chatbot_type_seq_idx ON proai.chatbot_contents USING btree (chatbot_id, type_cd, seq);


-- proai.chatbot_file definition

-- Drop table

-- DROP TABLE proai.chatbot_file;

CREATE TABLE proai.chatbot_file (
	chatbot_id int8 NOT NULL,
	file_id int8 NOT NULL,
	CONSTRAINT chatbot_file_pkey PRIMARY KEY (chatbot_id, file_id)
);


-- proai.chatroom definition

-- Drop table

-- DROP TABLE proai.chatroom;

CREATE TABLE proai.chatroom (
	id bigserial NOT NULL,
	chatbot_id int8 DEFAULT 0 NOT NULL,
	reg_user_id varchar(30) NULL,
	seq int4 NULL,
	title varchar(500) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT chatbot_room_pkey PRIMARY KEY (id)
);
CREATE INDEX chatroom_reg_user_id_idx ON proai.chatroom USING btree (reg_user_id, seq, id);


-- proai.chatroom_detail definition

-- Drop table

-- DROP TABLE proai.chatroom_detail;

CREATE TABLE proai.chatroom_detail (
	id bigserial NOT NULL,
	room_id int8 DEFAULT 0 NOT NULL,
	seq int8 NULL,
	"role" varchar(30) NULL,
	"content" text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	feedback varchar(1000) NULL,
	CONSTRAINT chatbot_room_list_pkey PRIMARY KEY (id)
);


-- proai.code definition

-- Drop table

-- DROP TABLE proai.code;

CREATE TABLE proai.code (
	cdgroup_id varchar(10) NOT NULL,
	cd_id varchar(10) NOT NULL,
	"name" varchar(100) NOT NULL,
	use_yn bool DEFAULT true NOT NULL,
	del_yn bool DEFAULT true NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT code_pkey PRIMARY KEY (cdgroup_id, cd_id)
);


-- proai.document_user definition

-- Drop table

-- DROP TABLE proai.document_user;

CREATE TABLE proai.document_user (
	id serial4 NOT NULL,
	account varchar(30) NOT NULL,
	"password" varchar(200) NOT NULL,
	CONSTRAINT document_user_pkey PRIMARY KEY (id)
);


-- proai.endpoint_info definition

-- Drop table

-- DROP TABLE proai.endpoint_info;

CREATE TABLE proai.endpoint_info (
	id varchar(20) NULL,
	"name" varchar(20) NULL,
	endpoint varchar(300) NULL,
	"key" varchar(50) NULL,
	"type" varchar(20) NULL,
	"desc" varchar(50) NULL,
	CONSTRAINT endpoint_info_unique UNIQUE (id)
);


-- proai.engine definition

-- Drop table

-- DROP TABLE proai.engine;

CREATE TABLE proai.engine (
	id bigserial NOT NULL,
	"type" varchar(10) DEFAULT NULL::character varying NULL,
	vendor varchar(10) DEFAULT NULL::character varying NULL,
	"name" varchar(50) NOT NULL,
	seq int4 NULL,
	apik varchar(100) NULL,
	endpoint varchar(200) NULL,
	model varchar(100) NULL,
	"version" varchar(100) NULL,
	parameters text NULL,
	parameters_additional text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT engine_pkey PRIMARY KEY (id)
);


-- proai.exec_info definition

-- Drop table

-- DROP TABLE proai.exec_info;

CREATE TABLE proai.exec_info (
	id bigserial NOT NULL,
	"result" varchar(100) DEFAULT NULL::character varying NULL,
	api varchar(100) DEFAULT NULL::character varying NULL,
	"time" int4 NULL,
	ip varchar(100) DEFAULT NULL::character varying NULL,
	CONSTRAINT exec_info_pkey PRIMARY KEY (id)
);


-- proai.kakao_user definition

-- Drop table

-- DROP TABLE proai.kakao_user;

CREATE TABLE proai.kakao_user (
	bot_user_key varchar(50) NOT NULL,
	app_user_id varchar(50) NULL,
	phone_number varchar(50) NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	CONSTRAINT kakao_user_pk PRIMARY KEY (bot_user_key)
);


-- proai."member" definition

-- Drop table

-- DROP TABLE proai."member";

CREATE TABLE proai."member" (
	user_key int8 NOT NULL,
	username varchar(50) NULL,
	"password" varchar(100) NULL,
	"name" varchar(50) NULL,
	vendor_user_key varchar(200) NULL,
	vendor_type varchar(10) NULL,
	receive_id varchar(20) NULL,
	vendor_user_id varchar(20) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	longitude float4 NULL,
	latitude float4 NULL,
	CONSTRAINT member_pk PRIMARY KEY (user_key)
);
CREATE INDEX member_name_idx ON proai.member USING btree (name);
CREATE INDEX member_vendor_type_id_idx ON proai.member USING btree (vendor_type, vendor_user_id);
CREATE INDEX member_vendor_type_key_idx ON proai.member USING btree (vendor_type, vendor_user_key);
CREATE INDEX member_vendor_type_keyidx ON proai.member USING btree (vendor_type, vendor_user_key);


-- proai.member_key_mapping definition

-- Drop table

-- DROP TABLE proai.member_key_mapping;

CREATE TABLE proai.member_key_mapping (
	vendor_type varchar(10) NOT NULL,
	vendor_user_key varchar(200) NULL,
	vendor_user_id varchar(20) NULL,
	receive_id varchar(20) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	"name" varchar(50) NULL,
	CONSTRAINT member_key_mapping_unique UNIQUE (vendor_type, vendor_user_id)
);


-- proai.question_generate definition

-- Drop table

-- DROP TABLE proai.question_generate;

CREATE TABLE proai.question_generate (
	id bigserial NOT NULL,
	engine_id int8 NOT NULL,
	system_prompt varchar(2000) DEFAULT NULL::character varying NULL,
	seq int4 NULL,
	top_p float8 DEFAULT 0.9 NULL,
	temperature float8 DEFAULT 0.5 NULL,
	pres_p float8 DEFAULT 1 NULL,
	freq_p float8 DEFAULT 1 NULL,
	max_token int4 DEFAULT 2048 NULL,
	use_yn bool NULL,
	CONSTRAINT question_generate_pkey PRIMARY KEY (id)
);


-- proai.routine_detail definition

-- Drop table

-- DROP TABLE proai.routine_detail;

CREATE TABLE proai.routine_detail (
	id bigserial NOT NULL,
	task_cd varchar(50) NOT NULL,
	seq int4 NULL,
	"type" varchar(10) NULL,
	"text" varchar(200) NULL,
	link varchar(200) NULL,
	link_pc varchar(200) NULL,
	CONSTRAINT routine_detail_pk PRIMARY KEY (id)
);


-- proai.routine_info definition

-- Drop table

-- DROP TABLE proai.routine_info;

CREATE TABLE proai.routine_info (
	id bigserial NOT NULL,
	task_cd varchar(50) NOT NULL,
	senario_name varchar(50) NULL,
	senario_ment text NULL,
	use_yn varchar(1) NULL,
	"desc" varchar(50) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	random_yn varchar(1) NULL,
	next_task_cd varchar(50) NULL,
	ad_yn varchar(1) NULL,
	CONSTRAINT routine_info_pk PRIMARY KEY (id)
);
CREATE INDEX routine_info_task_cd_idx ON proai.routine_info USING btree (task_cd);


-- proai.routine_schedule definition

-- Drop table

-- DROP TABLE proai.routine_schedule;

CREATE TABLE proai.routine_schedule (
	id bigserial NOT NULL,
	send_time timestamp NULL,
	task_cd varchar(50) NULL,
	receive_id varchar(20) NULL,
	"name" varchar(50) NULL,
	send_yn varchar(1) NULL,
	"result" text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT routine_schedule_pk PRIMARY KEY (id)
);
CREATE INDEX routine_schedule_task_cd_idx ON proai.routine_schedule USING btree (task_cd, send_time);


-- proai.source_file definition

-- Drop table

-- DROP TABLE proai.source_file;

CREATE TABLE proai.source_file (
	id serial4 NOT NULL,
	user_id varchar(30) DEFAULT NULL::character varying NULL,
	user_name varchar(30) DEFAULT NULL::character varying NULL,
	org_name varchar(100) DEFAULT NULL::character varying NULL,
	"name" varchar(100) DEFAULT NULL::character varying NULL,
	"path" varchar(300) DEFAULT NULL::character varying NULL,
	"size" int8 NULL,
	"type" varchar(100) DEFAULT NULL::character varying NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT source_file_pkey PRIMARY KEY (id)
);


-- proai.temp_message definition

-- Drop table

-- DROP TABLE proai.temp_message;

CREATE TABLE proai.temp_message (
	id bigserial NOT NULL,
	bot_user_key varchar(100) NULL,
	message text NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT temp_message_pk PRIMARY KEY (id)
);
CREATE INDEX temp_message_bot_user_key_idx ON proai.temp_message USING btree (bot_user_key, id DESC);


-- proai.temp_user_mapping definition

-- Drop table

-- DROP TABLE proai.temp_user_mapping;

CREATE TABLE proai.temp_user_mapping (
	user_name varchar(50) NULL,
	identity_no varchar(50) NULL,
	user_key int8 NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT temp_user_mapping_unique UNIQUE (user_key)
);







2. skins ddl

-- skins.api_users definition

-- Drop table

-- DROP TABLE skins.api_users;

CREATE TABLE skins.api_users (
	vendor_id varchar(50) NOT NULL,
	api_key varchar(50) NOT NULL,
	use_yn varchar(1) NULL,
	reg_id varchar(50) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT api_users_unique UNIQUE (vendor_id)
);


-- skins.citylab_hair_scalptype_detail definition

-- Drop table

-- DROP TABLE skins.citylab_hair_scalptype_detail;

CREATE TABLE skins.citylab_hair_scalptype_detail (
	surveyno int8 NULL,
	userkey int8 NULL,
	scalptype_position varchar(20) NULL,
	scalptype_nor int4 NULL,
	scalptype_oily int4 NULL,
	scalptype_ato int4 NULL,
	scalptype_trb int4 NULL,
	scalptype_dry int4 NULL,
	scalptype_sen int4 NULL,
	scalptype_seb int4 NULL,
	scalptype_ddan int4 NULL,
	scalptype_odan int4 NULL,
	scalptype_onknown int4 NULL,
	thickness float8 NULL,
	density float8 NULL,
	thickness_mean float8 NULL,
	density_mean float8 NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL
);


-- skins.citylab_hair_scalptype_main definition

-- Drop table

-- DROP TABLE skins.citylab_hair_scalptype_main;

CREATE TABLE skins.citylab_hair_scalptype_main (
	surveyno int8 NULL,
	userkey int8 NULL,
	scalptype_nor int4 NULL,
	scalptype_oily int4 NULL,
	scalptype_ato int4 NULL,
	scalptype_trb int4 NULL,
	scalptype_dry int4 NULL,
	scalptype_sen int4 NULL,
	scalptype_seb int4 NULL,
	scalptype_ddan int4 NULL,
	scalptype_odan int4 NULL,
	scalptype_onknown int4 NULL,
	hairlosstype_basic varchar(10) NULL,
	hairlosstype_center varchar(10) NULL,
	hairlosstype_frontcenter varchar(10) NULL,
	haircondition_type varchar(10) NULL,
	haircondition_root varchar(10) NULL,
	haircondition_mid varchar(10) NULL,
	haircondition_tip varchar(50) NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL
);


-- skins.citylab_manager definition

-- Drop table

-- DROP TABLE skins.citylab_manager;

CREATE TABLE skins.citylab_manager (
	id bigserial NOT NULL,
	center_cd varchar(8) NULL,
	manager_id varchar(20) NULL,
	"password" varchar(150) NULL,
	"name" varchar(50) NULL,
	manager_cd varchar(8) NULL,
	role_cd varchar(8) NULL,
	email varchar(100) NULL,
	pwd_updated_at timestamp NULL,
	password_lock int4 NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	skins_pwd varchar(150) NULL,
	CONSTRAINT citylab_manager_pk PRIMARY KEY (id),
	CONSTRAINT citylab_manager_unique UNIQUE (manager_id)
);


-- skins.citylab_member definition

-- Drop table

-- DROP TABLE skins.citylab_member;

CREATE TABLE skins.citylab_member (
	id bigserial NOT NULL,
	center_cd varchar(8) NULL,
	cstm_id varchar(50) NULL,
	ustm_id varchar(50) NULL,
	"name" varchar(50) NULL,
	phone varchar(20) NULL,
	birthday varchar(10) NULL,
	birth_cd varchar(1) NULL,
	sex varchar(1) NULL,
	user_type varchar(10) NULL,
	memo text NULL,
	"comment" text NULL,
	email varchar(100) NULL,
	nat_cd varchar(10) NULL,
	ra_cd varchar(10) NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	chat_updated timestamp NULL,
	site varchar(50) NULL,
	CONSTRAINT citylab_member_pk PRIMARY KEY (id)
);


-- skins.citylab_result_rpt definition

-- Drop table

-- DROP TABLE skins.citylab_result_rpt;

CREATE TABLE skins.citylab_result_rpt (
	surveyno int8 NOT NULL,
	surveydate varchar(10) NULL,
	userkey int8 NULL,
	skin_score int4 NULL,
	skin_gomin varchar(50) NULL,
	iscomplexity varchar(1) NULL,
	t_zone_result varchar(100) NULL,
	t_zone_position_num int4 NULL,
	u_zone_result varchar(100) NULL,
	u_zone_position_num int4 NULL,
	fizpatrick_color_point int4 NULL,
	solution_type_number varchar(10) NULL,
	sensitive_type_number varchar(10) NULL,
	specialtip_img bytea NULL,
	specialtip_stoke_img bytea NULL,
	specialtip_memo varchar(2000) NULL,
	manager_value varchar(50) NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL
);


-- skins.citylab_skin_concern definition

-- Drop table

-- DROP TABLE skins.citylab_skin_concern;

CREATE TABLE skins.citylab_skin_concern (
	surveyno int8 NOT NULL,
	userkey int8 NULL,
	pore float8 NULL,
	wrinkle float8 NULL,
	futurewrinkles float8 NULL,
	pigmentation float8 NULL,
	melanin float8 NULL,
	darkcircles float8 NULL,
	transdermal float8 NULL,
	redness float8 NULL,
	porphyrin float8 NULL,
	elasticity float8 NULL,
	tzone_moisture float8 NULL,
	tzone_oilskin float8 NULL,
	uzone_moisture float8 NULL,
	uzone_oilskin float8 NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT citylab_skin_concern_pk PRIMARY KEY (surveyno)
);


-- skins.citylab_visit_direct definition

-- Drop table

-- DROP TABLE skins.citylab_visit_direct;

CREATE TABLE skins.citylab_visit_direct (
	skey bigserial NOT NULL,
	reg_date timestamp NULL,
	rsvn_date date NULL,
	rsvn_time varchar(50) NULL,
	"name" varchar(50) NULL,
	course_flg varchar(1) NULL,
	phone varchar(50) NULL,
	sex varchar(1) NULL,
	birthdate varchar(8) NULL,
	birthdatetp varchar(1) NULL,
	cstmid varchar(50) NULL,
	ucstmid int8 NULL,
	userkey int8 NULL,
	surveyno int8 NULL,
	progress_flg varchar(10) NULL,
	vst_path varchar(1) NULL,
	vst_txt varchar(50) NULL,
	email varchar(100) NULL,
	"comment" varchar(1000) NULL,
	group_id int8 NULL,
	programcode varchar(8) NULL,
	apnonid varchar(50) NULL,
	brandcourse varchar(10) NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT citylab_visit_direct_pk PRIMARY KEY (skey)
);


-- skins.citylab_visit_schedule definition

-- Drop table

-- DROP TABLE skins.citylab_visit_schedule;

CREATE TABLE skins.citylab_visit_schedule (
	visitkey bigserial NOT NULL,
	skey int8 NULL,
	reg_date timestamp NULL,
	rsvn_date date NULL,
	rsvn_time varchar(50) NULL,
	"name" varchar(50) NULL,
	course_flg varchar(50) NULL,
	phone varchar(20) NULL,
	sex varchar(1) NULL,
	birthdate varchar(8) NULL,
	birthdatetp varchar(1) NULL,
	cstmid varchar(50) NULL,
	ucstmid int8 NULL,
	m_userkey int8 NULL,
	m_surveyno int8 NULL,
	m_memoyn varchar(1) NULL,
	vstflg varchar(1) NULL,
	progress_flg varchar(2) NULL,
	c_name varchar(50) NULL,
	c_phone varchar(50) NULL,
	c_sex varchar(1) NULL,
	c_birthdate varchar(8) NULL,
	c_progress_flg varchar(10) NULL,
	c_cstmid varchar(50) NULL,
	c_ucstmid int8 NULL,
	c_userkey int8 NULL,
	c_surveyno int8 NULL,
	c_memoyn varchar(1) NULL,
	cancelyn varchar(1) NULL,
	rsvn_flg varchar(1) NULL,
	vst_path varchar(1) NULL,
	vst_txt varchar(50) NULL,
	c_vst_path varchar(1) NULL,
	c_vst_txt varchar(50) NULL,
	email varchar(100) NULL,
	"comment" varchar(1000) NULL,
	programcode varchar(8) NULL,
	prog_name varchar(50) NULL,
	prog_cd varchar(8) NULL,
	gene_kit varchar(9) NULL,
	reservation_num varchar(9) NULL,
	agree_flag varchar(1) NULL,
	agree_flag_date timestamp NULL,
	domrmancy_flag varchar(1) NULL,
	domrmancy_flag_date timestamp NULL,
	withdraw_flag varchar(1) NULL,
	withdraw_date timestamp NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT citylab_visit_schedule_pk PRIMARY KEY (visitkey)
);


-- skins.tb_city_location definition

-- Drop table

-- DROP TABLE skins.tb_city_location;

CREATE TABLE skins.tb_city_location (
	id bigserial NOT NULL,
	country varchar(8) NULL,
	level1 varchar(50) NULL,
	level2 varchar(50) NULL,
	level3 varchar(50) NULL,
	gridx int8 NULL,
	gridy int8 NULL,
	lon_hour int8 NULL,
	lon_min int8 NULL,
	lon_sec float8 NULL,
	lat_hour int8 NULL,
	lat_min int8 NULL,
	lat_sec float8 NULL,
	longitude float8 NULL,
	latitude float8 NULL,
	CONSTRAINT tb_city_location_pk PRIMARY KEY (id)
);


-- skins.tb_code definition

-- Drop table

-- DROP TABLE skins.tb_code;

CREATE TABLE skins.tb_code (
	group_cd varchar(50) NOT NULL,
	cd_id varchar(50) NOT NULL,
	cd_name varchar(50) NULL,
	description varchar(200) NULL,
	memo varchar(1000) NULL,
	reg_id int8 NULL,
	create_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	update_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT tb_code_pk PRIMARY KEY (group_cd, cd_id)
);


-- skins.tb_code_group definition

-- Drop table

-- DROP TABLE skins.tb_code_group;

CREATE TABLE skins.tb_code_group (
	group_cd varchar(50) NOT NULL,
	up_group_cd varchar(50) NULL,
	group_name varchar(50) NULL,
	description varchar(200) NULL,
	memo varchar(1000) NULL,
	reg_id int8 NULL,
	create_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	update_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT tb_code_group_pk PRIMARY KEY (group_cd)
);


-- skins.tb_consult_detail definition

-- Drop table

-- DROP TABLE skins.tb_consult_detail;

CREATE TABLE skins.tb_consult_detail (
	id bigserial NOT NULL,
	consult_id int8 NULL,
	category_cd varchar(20) NULL,
	consult_data text NULL,
	CONSTRAINT tb_consult_detail_pk PRIMARY KEY (id)
);


-- skins.tb_consult_indirect definition

-- Drop table

-- DROP TABLE skins.tb_consult_indirect;

CREATE TABLE skins.tb_consult_indirect (
	userkey varchar(50) NULL,
	"name" varchar(10) NULL,
	consult_time timestamp NULL,
	manager varchar(10) NULL,
	consult_data text NULL,
	significant text NULL,
	consult_type varchar(50) NULL,
	id int8 NOT NULL,
	CONSTRAINT tb_consult_indirect_pk PRIMARY KEY (id)
);


-- skins.tb_consult_info definition

-- Drop table

-- DROP TABLE skins.tb_consult_info;

CREATE TABLE skins.tb_consult_info (
	id bigserial NOT NULL,
	consult_date timestamp NULL,
	userkey int8 NULL,
	consult_data text NULL,
	concern1 varchar(50) NULL,
	concern2 varchar(50) NULL,
	product varchar(200) NULL,
	significant text NULL,
	etc text NULL,
	create_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	update_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	features text NULL,
	consult_number int8 NULL,
	manager varchar(50) NULL,
	CONSTRAINT tb_consult_info_pk PRIMARY KEY (id)
);
CREATE INDEX _20240529_tb_consult_info_consult_date_idx ON skins.tb_consult_info USING btree (consult_date);
CREATE INDEX _20240529_tb_consult_info_userkey_idx ON skins.tb_consult_info USING btree (userkey, consult_date);
CREATE INDEX _20240602_tb_consult_info_consult_date_idx ON skins.tb_consult_info USING btree (consult_date);
CREATE INDEX _20240602_tb_consult_info_userkey_idx ON skins.tb_consult_info USING btree (userkey, consult_date);
CREATE INDEX tb_consult_info_consult_date_idx ON skins.tb_consult_info USING btree (consult_date);
CREATE INDEX tb_consult_info_consult_number_idx ON skins.tb_consult_info USING btree (consult_number, id, userkey);
CREATE INDEX tb_consult_info_userkey_idx ON skins.tb_consult_info USING btree (userkey, consult_date);


-- skins.tb_gene_detail definition

-- Drop table

-- DROP TABLE skins.tb_gene_detail;

CREATE TABLE skins.tb_gene_detail (
	id bigserial NOT NULL,
	survey_id int8 NULL,
	category_cd varchar(50) NULL,
	item_cd varchar(50) NULL,
	grade varchar(50) NULL,
	CONSTRAINT citylab_tb_gene_detail_pk PRIMARY KEY (id)
);


-- skins.tb_gene_detail_type definition

-- Drop table

-- DROP TABLE skins.tb_gene_detail_type;

CREATE TABLE skins.tb_gene_detail_type (
	id bigserial NOT NULL,
	survey_id int8 NULL,
	category_cd varchar(50) NULL,
	item_cd varchar(50) NULL,
	gene_type_cd varchar(50) NULL,
	my_type_cd varchar(50) NULL,
	CONSTRAINT citylab_tb_gene_detail_type_pk PRIMARY KEY (id)
);


-- skins.tb_gene_info definition

-- Drop table

-- DROP TABLE skins.tb_gene_info;

CREATE TABLE skins.tb_gene_info (
	id bigserial NOT NULL,
	survey_date timestamp NULL,
	user_key int8 NULL,
	created_dt timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT citylab_tb_gene_info_pk PRIMARY KEY (id)
);


-- skins.tb_logging definition

-- Drop table

-- DROP TABLE skins.tb_logging;

CREATE TABLE skins.tb_logging (
	id int8 NOT NULL,
	proccess_type varchar(50) NULL,
	proccess_code int4 NULL,
	process_name varchar(255) NULL,
	params varchar(200) NULL,
	reg_key int8 NULL,
	reg_date timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT tb_logging_pk PRIMARY KEY (id)
);


-- skins.tb_significant_group definition

-- Drop table

-- DROP TABLE skins.tb_significant_group;

CREATE TABLE skins.tb_significant_group (
	code text NULL,
	description text NULL,
	value int8 NULL
);


-- skins.tb_weather definition

-- Drop table

-- DROP TABLE skins.tb_weather;

CREATE TABLE skins.tb_weather (
	code text NULL,
	"name" text NULL,
	unit text NULL
);


-- skins.tb_weather_filter definition

-- Drop table

-- DROP TABLE skins.tb_weather_filter;

CREATE TABLE skins.tb_weather_filter (
	code text NULL,
	desciprtion text NULL,
	value int8 NULL
);


-- skins.tb_weather_range_filter definition

-- Drop table

-- DROP TABLE skins.tb_weather_range_filter;

CREATE TABLE skins.tb_weather_range_filter (
	code text NULL,
	desciprtion text NULL,
	value text NULL
); -->
