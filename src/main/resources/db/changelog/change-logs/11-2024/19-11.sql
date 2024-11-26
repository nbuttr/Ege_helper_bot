--liquibase formatted SQL

-- changeset nbuttr:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE user_roles AS ENUM ('ADMIN', 'USER');

CREATE TYPE user_registration_status AS ENUM ('COMPLETED', 'NOT_FINISHED');

CREATE TYPE image_types AS ENUM ('THEORY', 'PRACTICE');

CREATE TABLE IF NOT EXISTS "user" (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        chat_id BIGINT,
                        user_role user_roles,
                        user_reg_status user_registration_status,
                        user_first_name VARCHAR(255),
                        user_last_name  VARCHAR(255),
                        journal_id UUID UNIQUE
);


CREATE TABLE "journal" (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           section_id UUID,
                           progress INTEGER
);


CREATE TABLE "section" (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           paragraph UUID UNIQUE
);


CREATE TABLE "paragraph" (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             image_to_paragraph jsonb,
                             paragraph_name VARCHAR(255),
                             max_test_mark INTEGER,
                             curr_test_mark INTEGER
);



ALTER TABLE "user"
    ADD FOREIGN KEY(journal_id) REFERENCES "journal"(id)
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "journal"
    ADD FOREIGN KEY("section_id") REFERENCES "section"("id")
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "section"
    ADD FOREIGN KEY("paragraph") REFERENCES "paragraph"("id")
        ON UPDATE NO ACTION ON DELETE NO ACTION;