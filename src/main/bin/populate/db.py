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


def save_media(media, writer_id, director_id, producer_id):
    _cursor.execute('select id from Media where title = %s', (media.title,))
    row = _cursor.fetchone()
    if row:
        return row[0]

    add_media = ("insert into Media "
                 "(description, posterPath, productionCompany, releaseDate, runTime, slug, title, writerId, directorId, producerId)"
                 "values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)")

    sql_media = (media.description, media.poster_path, media.production_company,
                 media.release_date, media.run_time, media.slug, media.title,
                 writer_id, director_id, producer_id)

    _cursor.execute(add_media, sql_media)

    return _cursor.lastrowid


def save_movie(movie, writer_id, director_id, producer_id):
    media_id = save_media(movie, writer_id, director_id, producer_id)

    _cursor.execute('select id from Movies where id = %s', (media_id,))
    row = _cursor.fetchone()
    if row:
        return row[0]

    add_movie = ("insert into Movies "
                 "(id, boxOffice, budget, trailerPath)"
                 "values (%s, %s, %s, %s)")

    sql_movie = (media_id, movie.box_office,
                 movie.budget, movie.production_company)

    _cursor.execute(add_movie, sql_movie)

    _cnx.commit()

    return media_id


def save_celebrity(celebrity):
    _cursor.execute('select id from Celebrities where name = %s', (celebrity.name,))
    row = _cursor.fetchone()
    if row:
        return row[0]

    add_celebrity = ("insert into Celebrities"
                     "(biography, birthday, birthplace, name, profilePath)"
                     "values (%s, %s, %s, %s, %s)")

    try:
        sql_celebrity = (celebrity.biography, celebrity.birthday,
                         celebrity.birthplace, celebrity.name, celebrity.profile_path)

        _cursor.execute(add_celebrity, sql_celebrity)
    except Exception as e:
        print (celebrity.biography)
        raise e

    _cnx.commit()

    return _cursor.lastrowid


def save_character(celebrity_id, media_id, character):
    add_character = ("insert into Characters"
                     "(castOrder, name, celebrityId, mediaId)"
                     "values (%s, %s, %s, %s)")

    sql_character = (character.cast_order, character.name,
                     celebrity_id, media_id)

    _cursor.execute(add_character, sql_character)

    _cnx.commit()

    return _cursor.lastrowid
