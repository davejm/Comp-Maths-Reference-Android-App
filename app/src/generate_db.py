# Python3 script to generate database from markdown directory

import os
import sqlite3
import markdown2 

CWD = os.getcwd()

MD_DIR = 'markdown'
MD_PATH = os.path.join(CWD, MD_DIR)

DB_NAME = 'compmaths.db'
DB_PATH = os.path.join(CWD, 'main', 'assets', DB_NAME)

SCHEMA = """
BEGIN TRANSACTION;
CREATE TABLE "topic" (
    `_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    `name`	TEXT
);
CREATE TABLE "chapter_question" (
    `_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    `chapter_id`	INTEGER NOT NULL,
    `question`	TEXT NOT NULL,
	`answer`	TEXT NOT NULL,
    FOREIGN KEY(`chapter_id`) REFERENCES chapter(_id)
);
CREATE TABLE "chapter" (
    `_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    `topic_id`	INTEGER NOT NULL,
    `chapter_num`	INTEGER NOT_NULL,
    `name`	TEXT NOT NULL,
    `markdown`	TEXT,
    FOREIGN KEY(`topic_id`) REFERENCES `topic`(`_id`)
);
CREATE TABLE `android_metadata` (
    `locale`	TEXT DEFAULT 'en_US'
);
COMMIT;
"""


def parse_markdown(md):
    """Parse markdown from given file"""
    with open(md) as m:
        return markdown2.markdown(m.read())


def get_title(filename):
    """Retrieves title from a filename '##_Title_of_file'"""
    return filename[3:].replace("_", " ")

def insert_android_meta(cursor):
    cursor.execute('INSERT INTO android_metadata DEFAULT VALUES')

def insert_chapter(cursor, topic_id, chapter_num, title, text):
    """Execute SQL update query on chapter table's markdown field"""
    cursor.execute('INSERT INTO chapter (topic_id, chapter_num, name, markdown) VALUES (?, ?, ?, ?)',
                   (topic_id, chapter_num, title, text))


def insert_topic(cursor, topic_name):
    cursor.execute("INSERT INTO topic (name) VALUES (?)", (topic_name,))

def generate_db(path, cursor):
    """Cycle through markdown folders and update database using cursor"""
    cursor.executescript(SCHEMA)
    insert_android_meta(cursor)
    topic_counter = 1
    for topic in os.listdir(MD_PATH):
        insert_topic(cursor, get_title(topic))
        ch_dir = os.path.join(MD_PATH, topic)

        for i, chapter_folder in enumerate(os.listdir(ch_dir)):
            title = get_title(chapter_folder)
            m = parse_markdown(os.path.join(ch_dir, chapter_folder, "notes.md"))
            insert_chapter(cursor, topic_counter, i+1, title, m)

        topic_counter += 1


if __name__ == '__main__':
    try:
        os.remove(DB_PATH)
    except OSError:
        pass
    db = sqlite3.connect(DB_PATH)
    c = db.cursor()
    generate_db(DB_PATH, c)
    db.commit()
    db.close()