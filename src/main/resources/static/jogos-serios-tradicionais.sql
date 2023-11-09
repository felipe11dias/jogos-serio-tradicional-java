CREATE TYPE "ROLE" AS ENUM (
  'STUDENT',
  'TEACHER'
);

CREATE TYPE "TOKEN_TYPE" AS ENUM (
  'BEARER'
);

CREATE TABLE "TOKEN" (
  "token_id" BIGSERIAL PRIMARY KEY,
  "token" varchar,
  "type" varchar,
  "revoked" boolean,
  "expired" boolean,
  "user_id" bigint
);

CREATE TABLE "USER" (
  "user_id" BIGSERIAL PRIMARY KEY,
  "email" varchar,
  "name" varchar,
  "password" varchar,
  "role" varchar
);

CREATE TABLE "DISCIPLINE" (
  "discipline_id" BIGSERIAL PRIMARY KEY,
  "name" varchar,
  "theme" varchar,
  "user_id" bigint
);

CREATE TABLE "ACTIVITY" (
  "activity_id" BIGSERIAL PRIMARY KEY,
  "name" varchar,
  "discipline_id" bigint,
  "user_id" bigint
);

CREATE TABLE "RANKING" (
  "ranking_id" BIGSERIAL PRIMARY KEY,
  "questions_hit" bigint,
  "game" varchar,
  "time" varchar,
  "fulltime" varchar,
  "activity_id" bigint,
  "user_id" bigint
);

CREATE TABLE "QUESTION" (
  "question_id" BIGSERIAL PRIMARY KEY,
  "description" varchar,
  "activity_id" bigint,
  "answer_correct_id" bigint
);

CREATE TABLE "ANSWER" (
  "answer_id" BIGSERIAL PRIMARY KEY,
  "question_id" bigint,
  "description" varchar
);

ALTER TABLE "TOKEN" ADD FOREIGN KEY ("user_id") REFERENCES "USER" ("user_id");

ALTER TABLE "DISCIPLINE" ADD FOREIGN KEY ("user_id") REFERENCES "USER" ("user_id");

ALTER TABLE "ACTIVITY" ADD FOREIGN KEY ("discipline_id") REFERENCES "DISCIPLINE" ("discipline_id");

ALTER TABLE "ACTIVITY" ADD FOREIGN KEY ("user_id") REFERENCES "USER" ("user_id");

ALTER TABLE "RANKING" ADD FOREIGN KEY ("activity_id") REFERENCES "ACTIVITY" ("activity_id");

ALTER TABLE "RANKING" ADD FOREIGN KEY ("user_id") REFERENCES "USER" ("user_id");

ALTER TABLE "QUESTION" ADD FOREIGN KEY ("activity_id") REFERENCES "ACTIVITY" ("activity_id");

ALTER TABLE "ANSWER" ADD FOREIGN KEY ("question_id") REFERENCES "QUESTION" ("question_id");
