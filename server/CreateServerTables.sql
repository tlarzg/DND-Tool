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
  name character varying NOT NULL
);
ALTER TABLE campaign OWNER TO postgres;

CREATE TABLE user_campaigns (
  user_campaigns_id BIGSERIAL PRIMARY KEY,
  user_id bigint REFERENCES "user"(user_id),
  campaign_id bigint REFERENCES campaign(campaign_id)
);
ALTER TABLE user_campaigns OWNER TO postgres;
