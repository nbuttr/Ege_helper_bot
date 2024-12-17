CREATE TABLE "second_part"(
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              image_url VARCHAR(255),
                              paragraph_id UUID
);

ALTER TABLE "second_part"
    ADD FOREIGN KEY (paragraph_id) REFERENCES "paragraph"(id)
        ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE paragraph ADD COLUMN second_part_id UUID;