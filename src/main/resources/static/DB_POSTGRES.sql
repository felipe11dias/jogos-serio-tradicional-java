CREATE TABLE "USER" (
  "user_id" long PRIMARY KEY,
  "email" varchar,
  "name" varchar,
  "password" varchar,
  "role" enum
);

CREATE TABLE "DISCIPLINE" (
  "discipline_id" long PRIMARY KEY,
  "name" varchar,
  "theme" varchar,
  "activities" array,
  "user" user
);

CREATE TABLE "ACTIVITY" (
  "activity_id" long PRIMARY KEY,
  "name" varchar,
  "questioner" questioner,
  "user" user
);

CREATE TABLE "QUESTIONER" (
  "questioner_id" long PRIMARY KEY,
  "questions" array
);

CREATE TABLE "QUESTION" (
  "question_id" long PRIMARY KEY,
  "name" varchar,
  "answer" array,
  "idAnswerCorrect" long
);

CREATE TABLE "ANSWER" (
  "answer_id" long PRIMARY KEY,
  "description" varchar
);

ALTER TABLE "DISCIPLINE" ADD FOREIGN KEY ("discipline_id") REFERENCES "USER" ("user_id");

ALTER TABLE "ACTIVITY" ADD FOREIGN KEY ("activity_id") REFERENCES "USER" ("user_id");

ALTER TABLE "ACTIVITY" ADD FOREIGN KEY ("activity_id") REFERENCES "DISCIPLINE" ("discipline_id");

ALTER TABLE "QUESTIONER" ADD FOREIGN KEY ("questioner_id") REFERENCES "ACTIVITY" ("activity_id");

ALTER TABLE "QUESTION" ADD FOREIGN KEY ("question_id") REFERENCES "QUESTIONER" ("questioner_id");

ALTER TABLE "ANSWER" ADD FOREIGN KEY ("answer_id") REFERENCES "QUESTION" ("question_id");
