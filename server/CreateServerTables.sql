CREATE TABLE "user" (
  user_id BIGSERIAL PRIMARY KEY,
  username character varying UNIQUE NOT NULL,
  cipher_iv BYTEA NOT NULL,
  verification_secret BYTEA NOT NULL,
  verification_salt BYTEA NOT NULL
);
ALTER TABLE "user" OWNER TO postgres;

CREATE TABLE campaign (
  campaign_id BIGSERIAL PRIMARY KEY,
  name character varying NOT NULL,
  description character varying NOT null
);
ALTER TABLE campaign OWNER TO postgres;

CREATE TABLE character_campaigns (
  character_campaigns_id BIGSERIAL PRIMARY KEY,
  user_id bigint REFERENCES "user"(user_id),
  campaign_id bigint REFERENCES campaign(campaign_id)
);
ALTER TABLE character_campaigns OWNER TO postgres;

CREATE TABLE "character" (
  character_id BIGSERIAL PRIMARY KEY,
  user_id bigint REFERENCES "user"(user_id),
  name character varying NOT NULL
);

CREATE TABLE ability (
  ability_id BIGSERIAL PRIMARY KEY,
  name character varying NOT NULL,
  description character varying NOT NULL
);

CREATE TABLE character_abilities (
  character_abilities_id BIGSERIAL PRIMARY KEY,
  character_id BIGINT REFERENCES "character"(character_id),
  ability_id BIGINT REFERENCES ability(ability_id)
);
