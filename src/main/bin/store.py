import os
import argparse
from collections import namedtuple

import mysql.connector

import net, db
from deserialize import json_to_movie, json_to_genres


MAX_CAST_MEMBERS = 15
DEFAULT_MOVIES_PER_YEAR_LIMIT = 15


def store_celebrity(api_celebrity_id, basepath):
    celebrity = net.get_celebrity(api_celebrity_id)
    if basepath and celebrity.profile_path:
        net.download_file(net.get_profile_img_url(
            celebrity.profile_path), basepath)

    return db.save_celebrity(celebrity)

def store_cast(cast, media_id, basepath):
    for i, cast_member in enumerate(cast, 0):
        if i == MAX_CAST_MEMBERS:
            break
        celebrity_id = store_celebrity(cast_member.talent_id, basepath)
        db.save_character(celebrity_id, media_id, cast_member.character)


def store_movie_crew(movie_credits, basepath):
    api_writer = next(filter(
        lambda member: member.department.lower() == 'writing', movie_credits.crew), None)
    api_director = next(
        filter(lambda member: member.job.lower() == 'director', movie_credits.crew), None)

    api_producer = next(
        filter(lambda member: 'producer' in member.job.lower(), movie_credits.crew), None)

    writer_id, director_id, producer_id = None, None, None

    if api_writer:
        writer_id = store_celebrity(api_writer.talent_id, basepath)
    if api_director:
        director_id = store_celebrity(
            api_director.talent_id, basepath)
    if api_producer:
        producer_id = store_celebrity(
            api_producer.talent_id, basepath)

    return writer_id, director_id, producer_id


def store_movie(api_movie_id, basepath):
    movie = net.get_movie(api_movie_id)

    if basepath and movie.poster_path:
        net.download_file(net.get_poster_img_url(movie.poster_path), basepath)

    movie_credits = net.get_movie_credits(api_movie_id)
    writer_id, director_id, producer_id = store_movie_crew(
        movie_credits, basepath)
    movie_id = db.save_movie(movie, writer_id, director_id, producer_id)
    db.save_media_genres(movie_id, movie.genres)
    store_cast(movie_credits.cast, movie_id, basepath)

    print ('Saved movie: {}'.format(movie.title))

    return movie_id


def store_movies(start_year, end_year, limit, basepath):
    for year in range(start_year, end_year + 1):
        ids = net.get_top_movies(year, limit)
        for id in ids:
            store_movie(id, basepath)

def store_tvshow(api_tvshow_id, basepath):
    tvshow = net.get_tvshow(api_tvshow_id)

    if basepath and tvshow.poster_path:
        net.download_file(net.get_poster_img_url(tvshow.poster_path), basepath)

    tvshow_credits = net.get_tvshow_credits(api_tvshow_id)
    creator_id = store_celebrity(tvshow.api_creator_id, basepath)
    tvshow_id = db.save_tvshow(tvshow, creator_id)
    db.save_media_genres(tvshow_id, tvshow.genres)
    store_cast(tvshow_credits.cast, tvshow_id, basepath)

    print ('Saved tv show: {}'.format(tvshow.title))

    return tvshow_id