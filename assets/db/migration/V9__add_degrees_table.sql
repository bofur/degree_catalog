CREATE TABLE degrees(
   id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
   student_id INTEGER REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE,
   name TEXT NOT NULL,
   year INTEGER NOT NULL
);