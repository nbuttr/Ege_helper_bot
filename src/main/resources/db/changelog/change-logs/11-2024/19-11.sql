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
                        journal_id UUID
);


CREATE TABLE "journal" (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           section_id UUID,
                           progress INTEGER
);


CREATE TABLE "section" (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           section_name VARCHAR(255),
                           paragraph UUID
);


CREATE TABLE "paragraph" (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             image_to_paragraph jsonb,
                             paragraph_name VARCHAR(255),
                             max_test_mark INTEGER,
                             curr_test_mark INTEGER,
                             section_id UUID,
                             test UUID
);

CREATE TABLE "test" (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             paragraph_id UUID,
                             image_url VARCHAR(255),
                             answer_1 VARCHAR(255),
                             answer_2 VARCHAR(255),
                             answer_3 VARCHAR(255),
                             answer_4 VARCHAR(255),
                             correct_answer VARCHAR(255)
);

CREATE TABLE "user_paragraph_progress" (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             user_id UUID,
                             paragraph_id UUID,
                             test_score INTEGER DEFAULT 0,
                             max_test_score INTEGER
);


ALTER TABLE "journal"
    ADD FOREIGN KEY("section_id") REFERENCES "section"("id")
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "section"
    ADD FOREIGN KEY("paragraph") REFERENCES "paragraph"("id")
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "paragraph"
    ADD FOREIGN KEY (section_id) REFERENCES "section"(id)
        ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "test"
    ADD FOREIGN KEY (paragraph_id) REFERENCES "paragraph"(id)
        ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "user_paragraph_progress"
    ADD FOREIGN KEY (user_id) REFERENCES "user"(id)
        ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "user_paragraph_progress"
    ADD FOREIGN KEY (paragraph_id) REFERENCES "paragraph"(id)
        ON UPDATE CASCADE ON DELETE CASCADE;