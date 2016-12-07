CREATE TABLE DS_USER (
  ID            INT(11)      NOT NULL AUTO_INCREMENT,
  FIRSTNAME     VARCHAR(32)  NULL,
  LASTNAME      VARCHAR(32)  NULL,
  AGE           INT(3)       NULL,
  ACTIVE        INT(1)       NULL,
  CREATED_AT    TIMESTAMP    NULL,
  EMAIL_ADDRESS VARCHAR(128) NULL,
  MANAGER_ID    INT(11)      NULL,
  BINARY_DATA   BLOB         NULL,
  DATE_OF_BIRTH DATE         NULL,
  country       VARCHAR(64)  NULL,
  city          VARCHAR(64)  NULL,
  street_name   VARCHAR(64)  NULL,
  street_no     VARCHAR(64)  NULL,
  PRIMARY KEY (ID)
);
CREATE TABLE DS_ROLE (
  ID       INT(11)     NOT NULL AUTO_INCREMENT,
  NAME     VARCHAR(32) NULL,
  GROUP_ID INT(11)     NULL,
  PRIMARY KEY (ID)
);
CREATE TABLE DS_GROUP (
  ID   INT(11)     NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(32) NULL,
  CODE VARCHAR(32) NULL,
  PRIMARY KEY (ID)
);
CREATE TABLE DEPARTMENT (
  ID   INT(11)     NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(32) NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE DS_USER_DS_USER (
  DS_USER_ID    INT(11) NOT NULL,
  COLLEAGUES_ID INT(11) NOT NULL,
  PRIMARY KEY (DS_USER_ID, COLLEAGUES_ID)
);
CREATE TABLE DS_USER_DS_ROLE (
  DS_USER_ID INT(11) NOT NULL,
  DS_ROLE_ID INT(11) NOT NULL,
  PRIMARY KEY (DS_USER_ID, DS_ROLE_ID)
);
