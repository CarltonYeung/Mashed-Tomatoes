import os
import mysql.connector

_cnx = mysql.connector.connect(
    user=os.environ['MT_MYSQL_USER'],
    password=os.environ['MT_MYSQL_PASSWORD'],
    database=os.environ['MT_MYSQL_DB_NAME'])

_cursor = _cnx.cursor()

def close():
    _cnx.commit()
    _cursor.close()
    _cnx.close()

def save_media(media):
    add_media = ("INSERT INTO Media "
                 "(backdropPath, description, posterPath, releaseDate, runTime, slug, title) "
                 "VALUES (%s, %s, %s, %s, %s, %s, %s)")

    sql_media = (media.backdrop_path, media.description, media.poster_path,
                 media.release_date, media.run_time, media.slug, media.title)

    _cursor.execute(add_media, sql_media)

    return _cursor.lastrowid

def save_movie(movie):
    add_movie = ("INSERT INTO Movies "
                 "(ID, boxOffice, productionCompany)"
                 "VALUES (%s, %s, %s)")

    media_id = save_media(movie)

    sql_movie = (media_id, movie.box_office, movie.production_company)

    _cursor.execute(add_movie, sql_movie)

    _cnx.commit()

    return media_id

def save_celebrity(celebrity):
    add_celebrity = ("")

def save_movie_credits(movie_id, movie_credits):
    add_character = ("INSERT INTO Characters"
                     "(ID, castOrder, name)"
                     "VALUES (%s, %s, %s)")

    pass