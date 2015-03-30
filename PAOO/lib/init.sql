-- @author Kevin Bavay
DROP SCHEMA IF EXISTS business_days CASCADE;

CREATE SCHEMA business_days;

CREATE SEQUENCE business_days.users_id_seq;
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');
CREATE TABLE IF NOT EXISTS business_days.users (
  user_id       INTEGER PRIMARY KEY
    DEFAULT NEXTVAL('business_days.users_id_seq'),
  role user_role NOT NULL DEFAULT USER,
  password      VARCHAR(256)                NOT NULL,
  email         VARCHAR(50)                 NOT NULL,
  username      VARCHAR(25)                 NOT NULL UNIQUE,
  first_name    VARCHAR(25)                 NOT NULL,
  last_name     VARCHAR(25)                 NOT NULL,
  register_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  version       INTEGER                     NOT NULL DEFAULT 1
);

CREATE SEQUENCE business_days.companies_id_seq;
CREATE TABLE IF NOT EXISTS business_days.companies (
  company_id       INTEGER PRIMARY KEY
    DEFAULT NEXTVAL('business_days.companies_id_seq'),
  creator          INTEGER REFERENCES business_days.users (user_id)  NOT NULL,
  name             VARCHAR(25)                                       NOT NULL UNIQUE,
  register_date    TIMESTAMP WITHOUT TIME ZONE                       NOT NULL DEFAULT now(),
  address_street    VARCHAR(50)                                       NOT NULL,
  address_num       VARCHAR(10)                                       NOT NULL,
  address_mailbox  VARCHAR(10),
  address_postcode VARCHAR(10)                                           NOT NULL,
  address_town     VARCHAR(25)                                       NOT NULL,
  version          INTEGER                                           NOT NULL DEFAULT 1
);

CREATE SEQUENCE business_days.contacts_id_seq;
CREATE TABLE IF NOT EXISTS business_days.contacts (
  contact_id  INTEGER PRIMARY KEY
    DEFAULT NEXTVAL('business_days.contacts_id_seq'),
  company     INTEGER REFERENCES business_days.companies (company_id)   NOT NULL,
  email       VARCHAR(50),
  email_valid BOOLEAN                                                            DEFAULT TRUE,
  first_name  VARCHAR(25)                                               NOT NULL,
  last_name   VARCHAR(25)                                               NOT NULL,
  phone       VARCHAR(25),
  version     INTEGER                                                   NOT NULL DEFAULT 1
);

CREATE SEQUENCE business_days.business_days_id_seq;
CREATE TABLE IF NOT EXISTS business_days.business_days (
  business_day_id INTEGER PRIMARY KEY
    DEFAULT NEXTVAL('business_days.business_days_id_seq'),
  event_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  creation_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  academic_year   CHAR(9)                     NOT NULL UNIQUE,
  version         INTEGER                     NOT NULL DEFAULT 1
);

CREATE TYPE participation_state AS ENUM ('INVITED', 'CONFIRMED', 'DECLINED', 'BILLED', 'PAID');
CREATE TABLE IF NOT EXISTS business_days.participations (
  company      INTEGER REFERENCES business_days.companies (company_id)           NOT NULL,
  business_day INTEGER REFERENCES business_days.business_days (business_day_id)  NOT NULL,
  state        participation_state                                               NOT NULL DEFAULT 'INVITED',
  cancelled    BOOLEAN                                                           NOT NULL DEFAULT FALSE,
  version      INTEGER                                                           NOT NULL DEFAULT 1,

  PRIMARY KEY (company, business_day)
);

CREATE TABLE IF NOT EXISTS business_days.attendances (
  company      INTEGER REFERENCES business_days.companies (company_id)          NOT NULL,
  business_day INTEGER REFERENCES business_days.business_days (business_day_id) NOT NULL,
  contact      INTEGER REFERENCES business_days.contacts (contact_id)           NOT NULL,

  PRIMARY KEY (company, business_day, contact)
);

-- password is password
--TRUNCATE business_days.users CASCADE;

INSERT INTO business_days.users VALUES (DEFAULT, 'USER', 'pbkdf2:7dbca107d1bc0416ae813ecaa44e443d0068b08c37ed4b4aa5d6d74c2d9dd33587a1d35504c8bbbc714920b4818b483d654efad4debb14ff34fd85464fe91d93:8214f59f6939c1e14dd30324790c4592:1000', 'email', 'UserName', 'FirstName', 'lastName', DEFAULT, DEFAULT);
INSERT INTO business_days.users VALUES (DEFAULT, 'USER', 'pbkdf2:7dbca107d1bc0416ae813ecaa44e443d0068b08c37ed4b4aa5d6d74c2d9dd33587a1d35504c8bbbc714920b4818b483d654efad4debb14ff34fd85464fe91d93:8214f59f6939c1e14dd30324790c4592:1000', 'email2', 'UserName2', 'FirstName2', 'lastName2', DEFAULT, DEFAULT);

INSERT INTO business_days.companies
VALUES (DEFAULT, 1, 'Microbrost', DEFAULT, 'Avenue Landmark', 42, NULL, 1000, 'Moscow', DEFAULT);
INSERT INTO business_days.companies VALUES
  (DEFAULT, 1, 'Pinapple', DEFAULT, 'Rue Le long couteau', 42, NULL, 10000, 'Saint-Jude', DEFAULT);

INSERT INTO business_days.contacts
VALUES (DEFAULT, 1, 'alahackbar@isis.afriqua', DEFAULT, 'Sarah', 'Croche', '098765432', DEFAULT);
INSERT INTO business_days.contacts
VALUES (DEFAULT, 1, 'monte@negro.swag', DEFAULT, 'Jesus', 'Christ et parle', '098765432', DEFAULT);

INSERT INTO business_days.business_days VALUES (DEFAULT, '2015-04-04', DEFAULT, '2014-2015');
INSERT INTO business_days.business_days VALUES (DEFAULT, '2015-04-04', DEFAULT, '2012-2013');

INSERT INTO business_days.participations VALUES (1, 1, 'INVITED', DEFAULT);
INSERT INTO business_days.participations VALUES (2, 1, 'DECLINED', DEFAULT);

INSERT INTO business_days.attendances VALUES (1, 1, 1);
INSERT INTO business_days.attendances VALUES (1, 1, 2);
