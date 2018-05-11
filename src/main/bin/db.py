import os

import dateutil.parser
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

_cursor = _cnx.cursor(buffered=True)


def close():
    _cnx.commit()
    _cursor.close()
    _cnx.close()


def save_media(media):
    add_media = ("insert into Media "
                 "(description, posterPath, productionCompany, title)"
                 "values (%s, %s, %s, %s)")

    sql_media = (media.description, media.poster_path,
                 media.production_company, media.title)

    _cursor.execute(add_media, sql_media)

    _cnx.commit()

    return _cursor.lastrowid


def media_in_db(media):
    _cursor.execute('select id from Media where title = %s and posterPath = %s', (media.title, media.poster_path))
    row = _cursor.fetchone()
    if row:
        return row[0]
    return None


def save_media_genres(media_id, genres):
    add_media = ("insert into MediaGenres"
                 "(mediaID, genre)"
                 "values (%s, %s)")

    valid_genres = map(lambda genre: genre.upper(), genres)
    valid_genres = filter(lambda genre: genre in VALID_GENRES, valid_genres)
    if not valid_genres:
        valid_genres.append('ACTION')  # default genre

    valid_genres = list(set(valid_genres))

    for genre in valid_genres:
        try:
            sql_media = (media_id, genre)
            _cursor.execute(add_media, sql_media)
            _cnx.commit()
        except mysql.connector.errors.IntegrityError as e:
            continue


def save_movie(movie, writer_id, director_id, producer_id):
    media_id = save_media(movie)

    add_movie = ("insert into Movies "
                 "(id, boxOffice, budget, releaseDate, runTime, writerId, directorId, producerId)"
                 "values (%s, %s, %s, %s, %s, %s, %s, %s)")

    sql_movie = (media_id, movie.box_office, movie.budget, movie.release_date,
                 movie.run_time, writer_id, director_id, producer_id)

    _cursor.execute(add_movie, sql_movie)

    _cnx.commit()

    return media_id


def save_tvshow(tvshow, creator_id):
    media_id = save_media(tvshow)

    add_tvshow = ("insert into TVShows "
                  "(id, endDate, episodeRunTime, episodes, network, seasons, startDate, creatorId)"
                  "values (%s, %s, %s, %s, %s, %s, %s, %s)")

    sql_tvshow = (media_id, tvshow.end_date, tvshow.episode_run_time, tvshow.episodes,
                  tvshow.network_company, tvshow.seasons, tvshow.start_date, creator_id)

    _cursor.execute(add_tvshow, sql_tvshow)

    _cnx.commit()

    return media_id


def save_tvshow_air_dates(tvshow_id, air_dates):
    for air_date in air_dates:
        add_air_date = ("insert into TVShowAirDates"
                        "(mediaId, airDate)"
                        "values (%s, %s)")

        sql_air_date = (tvshow_id, air_date)
        try:
            _cursor.execute(add_air_date, sql_air_date)
        except mysql.connector.errors.IntegrityError:
            continue  # sometimes duplicate dates slip through the cracks

    _cnx.commit()


def save_celebrity(celebrity):
    add_celebrity = ("insert into Celebrities"
                     "(biography, birthday, birthplace, name, profilePath)"
                     "values (%s, %s, %s, %s, %s)")

    try:
        sql_celebrity = (celebrity.biography, celebrity.birthday,
                         celebrity.birthplace, celebrity.name, celebrity.profile_path)

        _cursor.execute(add_celebrity, sql_celebrity)
    except Exception as e:
        raise e

    _cnx.commit()

    return _cursor.lastrowid


def celebrity_in_db(celebrity):
    _cursor.execute(
        'select id,birthday from Celebrities where name = %s and birthday = %s', (celebrity.name, celebrity.birthday))
    row = _cursor.fetchone()
    if row:
        return row[0]
    return None


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


def character_in_db(media_id, character):
    _cursor.execute(
        'select mediaId from Characters where mediaId = %s and name = %s', (media_id, character.name,))
    row = _cursor.fetchone()
    if row:
        return row[0]
    return None


def save_oscar_winner_set(best_picture_id, best_cinema_id,
                          best_editing_id, best_doc_id,
                          best_animated_id, year):
    add_winner = ("insert into OscarWinnerSet"
                  "(bestPictureId, bestCinematographyId, bestAnimatedFeatureId, bestDocumetaryFeatureId, bestFilmEditingId, year)"
                  "values (%s, %s, %s, %s, %s, %s)")

    sql_winner = (
        best_picture_id, best_cinema_id, best_animated_id, best_doc_id, best_editing_id,
        dateutil.parser.parse(str(year)))

    _cursor.execute(add_winner, sql_winner)

    _cnx.commit()

    return _cursor.lastrowid
