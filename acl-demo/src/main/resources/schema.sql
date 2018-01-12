-- MySQL Schema
USE acldb;

DROP TABLE IF EXISTS ropa_owner;
DROP TABLE IF EXISTS Ropa;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity; 
DROP TABLE IF EXISTS acl_class;   
DROP TABLE IF EXISTS acl_sid;


CREATE TABLE acl_sid (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN NOT NULL,
    sid VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

CREATE TABLE acl_class (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

CREATE TABLE acl_object_identity (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class BIGINT UNSIGNED NOT NULL,
    object_id_identity BIGINT NOT NULL,
    parent_object BIGINT UNSIGNED,
    owner_sid BIGINT UNSIGNED,
    entries_inheriting BOOLEAN NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

CREATE TABLE acl_entry (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order INTEGER NOT NULL,
    sid BIGINT UNSIGNED NOT NULL,
    mask INTEGER UNSIGNED NOT NULL,
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

--
CREATE TABLE User (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  role_id varchar(255) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE Ropa (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE ropa_owner (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  owner_id bigint(20) NOT NULL,
  ropa_id bigint(20) NOT NULL ,
  CONSTRAINT fk_userropa_user FOREIGN KEY (owner_id) REFERENCES User (id),
  CONSTRAINT fk_userropa_ropa FOREIGN KEY (ropa_id) REFERENCES Ropa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;







