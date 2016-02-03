# Python3 script to parse markdown and save to database

## WARNING THIS IS ALL UNTESTED! ##
## Commented out for safety when ##
## running gradle builds.        ##

# import os
# import sqlite3
# import markdown2 

# CWD = os.getcwd()

# MD_DIR = 'markdown'
# MD_PATH = os.path.join(CWD, MD_DIR)

# DB_NAME = 'compmaths.db'
# DB_PATH = os.path.join(CWD, 'main', 'assets', DB_NAME)
# TABLE = 'chapter'
# COLUMN = 'markdown'


# def parse_markdown(md):
	# """Parse markdown from given file"""
	# with open(md) as m:
		# return markdown2.markdown(m.read())

	
# def update_chapter_markdown(cursor, topic_id, chapter_num, text):
	# """Execute SQL update query on chapter table's markdown field"""
	# cursor.execute('UPDATE {table} SET {column} = "{text}", chapter_num = {chapter_num} WHERE topic_id = {topic_id};'.format(
		# table=TABLE, column=COLUMN, topic_id, chapter_num, text))
	
	
# def update_db(path, cursor):
	# """Cycle through markdown folders and update database using cursor"""
	# topic_counter = 1
		# for topic in os.listdir(MD_PATH):
			# ch_dir = os.path.join(MD_PATH, topic)
			# for i, md in enumerate(ch_dir):
				# m = parse_markdown(os.path.join(ch_dir, md))
				# update_chapter_markdown(cursor, topic_counter, i+1, md)
		# topic_counter += 1

# if __name__ == '__main__':
	# db = sqlite3.connect(DB_PATH)
	# c = db.cursor()
	# update_db(DB_PATH, c)
	# db.commit()
	# db.close()