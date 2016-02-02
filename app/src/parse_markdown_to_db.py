

DB_PATH = # Full path to database
MD_PATH = # Path to markdown directory

DB = # open database at DB_PATH
TABLE = 'chapter'
COLUMN = 'markdown'
	
def parse_markdown(file):
	# open_file
	# parse_markdown
	# close_file
	# return parsed_markdown
	
	
def get_chapter_id(file):
	# return file[0:2]

	
def write_text_to_db(database, table, column, topic_id, chapter_num, text):
	'UPDATE {table} SET {column} = "{text}", chapter_num = {chapter_num} WHERE topic_id = {topic_id};'.format(
		table, column, text, chapter_num, topic_id)
	
	
def update_db(path):
	topic_counter = 1
	for topic in MD_PATH:
		for idx, f in enumerate(os.path.join(MD_PATH, topic)):
			md = parse_markdown(f)
			write_text_to_db(DB, TABLE, COLUMN, topic_counter, idx+1, md)
		topic_counter += 1

if __name__ == '__main__':
	update_db()
	
(0, '01_intro'), (1, '02_whaterver')