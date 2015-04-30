-- @author Kevin Bavay
DROP SCHEMA IF EXISTS business_days CASCADE;

CREATE SCHEMA business_days;

CREATE SEQUENCE business_days.users_id_seq;
CREATE TYPE business_days.user_role AS ENUM ('USER', 'ADMIN');
CREATE TABLE IF NOT EXISTS business_days.users (
  user_id       INTEGER PRIMARY KEY
    DEFAULT NEXTVAL('business_days.users_id_seq'),
  role          business_days.user_role                   NOT NULL DEFAULT 'USER',
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
                                                                              DEFAULT NEXTVAL(
                                                                                  'business_days.companies_id_seq'),
  creator          INTEGER REFERENCES business_days.users (user_id)  NOT NULL,
  name             VARCHAR(50)                                       NOT NULL UNIQUE,
  register_date    TIMESTAMP WITHOUT TIME ZONE                       NOT NULL DEFAULT now(),
  address_street   VARCHAR(50)                                       NOT NULL,
  address_num      VARCHAR(10)                                       NOT NULL,
  address_mailbox  VARCHAR(10),
  address_postcode VARCHAR(10)                                       NOT NULL,
  address_town     VARCHAR(25)                                       NOT NULL,
  version          INTEGER                                           NOT NULL DEFAULT 1
);

CREATE SEQUENCE business_days.contacts_id_seq;
CREATE TABLE IF NOT EXISTS business_days.contacts (
  contact_id  INTEGER PRIMARY KEY
                                                                                 DEFAULT NEXTVAL(
                                                                                     'business_days.contacts_id_seq'),
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
                                                       DEFAULT NEXTVAL(
                                                           'business_days.business_days_id_seq'),
  event_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  creation_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  academic_year   INTEGER                     NOT NULL UNIQUE,
  version         INTEGER                     NOT NULL DEFAULT 1
);

CREATE TYPE business_days.participation_state AS ENUM ('INVITED', 'CONFIRMED', 'DECLINED', 'BILLED', 'PAID');
CREATE TABLE IF NOT EXISTS business_days.participations (
  company      INTEGER REFERENCES business_days.companies (company_id)           NOT NULL,
  business_day INTEGER REFERENCES business_days.business_days (business_day_id)  NOT NULL,
  state        business_days.participation_state                                               NOT NULL DEFAULT 'INVITED',
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


INSERT INTO business_days.users VALUES (DEFAULT, 'ADMIN', 'pbkdf2:7dbca107d1bc0416ae813ecaa44e443d0068b08c37ed4b4aa5d6d74c2d9dd33587a1d35504c8bbbc714920b4818b483d654efad4debb14ff34fd85464fe91d93:8214f59f6939c1e14dd30324790c4592:1000', 'dgrolaux@ipl.be', 'dgrolaux', 'Donatien', 'Grolaux', DEFAULT, DEFAULT);
INSERT INTO business_days.users VALUES (DEFAULT, 'ADMIN', 'pbkdf2:7dbca107d1bc0416ae813ecaa44e443d0068b08c37ed4b4aa5d6d74c2d9dd33587a1d35504c8bbbc714920b4818b483d654efad4debb14ff34fd85464fe91d93:8214f59f6939c1e14dd30324790c4592:1000', 'blehmann@ipl.be', 'blehmann', 'Brigitte', 'Lehmann', DEFAULT, DEFAULT);

INSERT INTO business_days.business_days VALUES (DEFAULT, '2010-10-27', DEFAULT, 2010, DEFAULT);
INSERT INTO business_days.business_days VALUES (DEFAULT, '2011-11-16', DEFAULT, 2011, DEFAULT);
INSERT INTO business_days.business_days VALUES (DEFAULT, '2012-11-07', DEFAULT, 2012, DEFAULT);
INSERT INTO business_days.business_days VALUES (DEFAULT, '2013-11-13', DEFAULT, 2013, DEFAULT);
INSERT INTO business_days.business_days VALUES (DEFAULT, '2014-11-12', DEFAULT, 2014, DEFAULT);


INSERT INTO business_days.companies
VALUES (DEFAULT, 1, 'Accenture', '2009-01-01', 'Waterloolaan ', '16', NULL, '1000', 'Bruxelles', DEFAULT);
-- Participations
INSERT INTO business_days.participations VALUES (1, 3, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (1, 4, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (1, 5, 'PAID', DEFAULT, DEFAULT);
-- Contacts
INSERT INTO business_days.contacts
VALUES (DEFAULT, 1, 'stephanie.de.troyer@accenture.com', DEFAULT, 'Stéphanie', 'De troyer', NULL, DEFAULT);
INSERT INTO business_days.contacts
VALUES (DEFAULT, 1, 'marijke.vandyck@accenture.com', DEFAULT, 'Marijke', 'Van Dyck', NULL, DEFAULT);
INSERT INTO business_days.contacts
VALUES (DEFAULT, 1, 'aimee.marecaux@accenture.com', DEFAULT, 'Aimée', 'Marecaux', NULL, DEFAULT);
-- Attendances
INSERT INTO business_days.attendances VALUES (1, 3, 1);
INSERT INTO business_days.attendances VALUES (1, 4, 2);
INSERT INTO business_days.attendances VALUES (1, 4, 3);
INSERT INTO business_days.attendances VALUES (1, 5, 3);


INSERT INTO business_days.companies
VALUES (DEFAULT, 1, 'CodeltBlue', '2009-01-01', 'Avenue de l’espérance', '40', 'b', '1348', 'Louvain-La-Neuve', DEFAULT);
-- Participations
INSERT INTO business_days.participations VALUES (2, 1, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (2, 2, 'DECLINED', DEFAULT, DEFAULT);
-- Contacts
INSERT INTO business_days.contacts
VALUES (DEFAULT, 2, 'Vincent.lepape@CodeItBlue.com', DEFAULT, 'Vincent', 'Lepape', '0479/97.95.05', DEFAULT);
-- Attendances
INSERT INTO business_days.attendances VALUES (2, 2, 4);


INSERT INTO business_days.companies
VALUES (DEFAULT, 1, 'STERIA', '2012-01-01', 'Boulevard du Souverain', '36', NULL, '1170', 'Bruxelles', DEFAULT);
-- Participations
INSERT INTO business_days.participations VALUES (3, 1, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (3, 2, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (3, 3, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (3, 4, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (3, 5, 'PAID', DEFAULT, DEFAULT);
-- Contacts
INSERT INTO business_days.contacts
VALUES (DEFAULT, 3, 'roberto.alvarez@steria.be', DEFAULT, 'Roberto', 'Alvarez', NULL, DEFAULT);
-- Attendances
INSERT INTO business_days.attendances VALUES (3, 1, 5);
INSERT INTO business_days.attendances VALUES (3, 2, 5);
INSERT INTO business_days.attendances VALUES (3, 3, 5);
INSERT INTO business_days.attendances VALUES (3, 4, 5);
INSERT INTO business_days.attendances VALUES (3, 5, 5);


INSERT INTO business_days.companies
VALUES (DEFAULT, 2, 'Eezee-IT', '2012-01-01', 'rue André Dumont', '5', NULL, '1435', 'Mont-Saint-Guibert', DEFAULT);
-- Participations
INSERT INTO business_days.participations VALUES (4, 4, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (4, 5, 'DECLINED', DEFAULT, DEFAULT);
-- Contacts
INSERT INTO business_days.contacts
VALUES (DEFAULT, 4, 'nicolas.rigo@eezee-it.com', DEFAULT, 'Nicolas', 'Rigo', '+32 478 88 02 55', DEFAULT);
-- Attendances
INSERT INTO business_days.attendances VALUES (4, 4, 6);


INSERT INTO business_days.companies
VALUES (DEFAULT, 2, 'Bewan', '2011-01-01', 'Drève Richelle', '161L', '46', '1410', 'Waterloo', DEFAULT);
-- Participations
INSERT INTO business_days.participations VALUES (5, 2, 'PAID', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (5, 3, 'DECLINED', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (5, 4, 'DECLINED', DEFAULT, DEFAULT);
INSERT INTO business_days.participations VALUES (5, 5, 'PAID', DEFAULT, DEFAULT);
-- Contacts
INSERT INTO business_days.contacts 
VALUES (DEFAULT, 5, 'Virginie.BRASSINNE@bewan.be', DEFAULT, 'Virginie', 'Brassinne', NULL, DEFAULT);
INSERT INTO business_days.contacts 
VALUES (DEFAULT, 5, 'isabelle.croiset@bewan.be', DEFAULT, 'Isabelle', 'Croiset', NULL, DEFAULT);
INSERT INTO business_days.contacts 
VALUES (DEFAULT, 5, 'Dedecker drh@bewan.b', DEFAULT, 'Bénédicte', 'Dedecker', NULL, DEFAULT);
-- Attendances
INSERT INTO business_days.attendances VALUES (5, 2, 7);
INSERT INTO business_days.attendances VALUES (5, 2, 8);
INSERT INTO business_days.attendances VALUES (5, 5, 9);


INSERT INTO business_days.users VALUES (DEFAULT, 'ADMIN', 'pbkdf2:81a2626cd1753863c79b61ddcec76a01200417296d3180652385111c051f67edbe25045b8ce1c6efc9596b0b7cec2cedc751110b9ee0f5521249872d0ed13714:49f148ee80fd772c7e63d2e784ff757c:1000', 'Ades@enfer.be', 'admin', 'Ades', 'Mars', DEFAULT, DEFAULT);
