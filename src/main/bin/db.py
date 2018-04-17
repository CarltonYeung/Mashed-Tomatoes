import os
import mysql.connector

CHARACTER_NAME_WIDTH = 255

VALID_GENRES = [
    'ACTION',
    'ADVENTURE',
    'ANIMATION',
    'BIOGRAPHY',
    'COMEDY',
    'CRIME',
    'DOCUMENTARY',
    'DRAMA',
    'FAMILY',
    'FANTASY',
    'GAMESHOW',
    'HISTORY',
    'HORROR',
    'MUSIC',
    'MUSICAL',
    'MYSTERY',
    'NEWS',
    'REALITYTV',
    'ROMANCE',
    'SCIFI',
    'SHORT',
    'SPORT',
    'TALKSHOW',
    'THRILLER',
    'WAR',
    'WESTERN'
]


_cnx = mysql.connector.connect(
    host=os.environ['MT_MYSQL_DB_HOST'],
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

    _cnx.commit()

    return _cursor.lastrowid


def save_media_genres(media_id, genres):
    add_media = ("insert into MediaGenres"
                 "(mediaID, genre)"
                 "values (%s, %s)")

    valid_genres = map(lambda genre: genre.upper(), genres)
    valid_genres = filter(lambda genre: genre in VALID_GENRES, valid_genres)
    if not valid_genres:
        valid_genres.append('ACTION') # default genre

    valid_genres = list(set(valid_genres))

    for genre in valid_genres:
        try:
            sql_media = (media_id, genre)
            _cursor.execute(add_media, sql_media)
            _cnx.commit()
        except mysql.connector.errors.IntegrityError as e:
            continue


def save_movie(movie, writer_id, director_id, producer_id):
    media_id = save_media(movie, writer_id, director_id, producer_id)

    _cursor.execute('select id from Movies where id = %s', (media_id,))
    row = _cursor.fetchone()
    if row:
        return row[0]

    add_movie = ("insert into Movies "
                 "(id, boxOffice, budget)"
                 "values (%s, %s, %s)")

    sql_movie = (media_id, movie.box_office, movie.budget)

    _cursor.execute(add_movie, sql_movie)

    _cnx.commit()

    return media_id


def save_celebrity(celebrity):
    _cursor.execute(
        'select id from Celebrities where name = %s', (celebrity.name,))
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

    character_name = character.name
    if len(character.name) > CHARACTER_NAME_WIDTH:
        character_name = character.name[:CHARACTER_NAME_WIDTH]

    sql_character = (character.cast_order, character_name,
                     celebrity_id, media_id)

    _cursor.execute(add_character, sql_character)

    _cnx.commit()

    return _cursor.lastrowid

def save_best_picture_winner(movie_id, year):
    add_winner = ("insert into BestPictureWinners"
                  "(movieId, year)"
                  "values (%s, %s)")
    
    sql_winner = (movie_id, year)

    _cursor.execute(add_winner, sql_winner)

    _cnx.commit()

    return _cursor.lastrowid
