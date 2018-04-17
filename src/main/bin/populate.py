#!/usr/bin/env python3

import os
import argparse
from collections import namedtuple

import mysql.connector

from populate import net, db
from populate.deserialize import json_to_movie, json_to_genres


MAX_CAST_MEMBERS = 15
DEFAULT_MOVIES_PER_YEAR_LIMIT = 15


def store_celebrity(api_celebrity_id, basepath):
    celebrity = net.get_celebrity(api_celebrity_id)
    if basepath and celebrity.profile_path:
        net.download_file(net.get_profile_img_url(
            celebrity.profile_path), basepath)

    return db.save_celebrity(celebrity)


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
    # print (movie)

    if basepath and movie.poster_path:
        net.download_file(net.get_poster_img_url(movie.poster_path), basepath)

    movie_credits = net.get_movie_credits(api_movie_id)
    writer_id, director_id, producer_id = store_movie_crew(
        movie_credits, basepath)
    movie_id = db.save_movie(movie, writer_id, director_id, producer_id)
    db.save_media_genres(movie_id, movie.genres)
    for i, cast_member in enumerate(movie_credits.cast, 0):
        if i == MAX_CAST_MEMBERS:
            break
        celebrity_id = store_celebrity(
            cast_member.talent_id, basepath)
        db.save_character(celebrity_id, movie_id, cast_member.character)

    print ('Saved movie: {}'.format(movie.title))

    return movie_id


def store_movies(start_year, end_year, limit, basepath):
    for year in range(start_year, end_year + 1):
        ids = net.get_top_movies(year, limit)
        for id in ids:
            store_movie(id, basepath)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='Populate database with data from the Movie Database')
    parser.add_argument('--basepath', default=None,
                        help='Path in which to store images, if none given, images are not stored')
    parser.add_argument('--start-year', default=1950,
                        help='Year in which to start downloading movies from (default: 1950)')
    parser.add_argument('--end-year', default=2018,
                        help='Year in which to end downloading movies for (default: 2018)')
    parser.add_argument('--limit', default=2018,
                        help='Amount of movies to store per year (default: 15)')

    args = parser.parse_args()
    store_movie(550, args.basepath)
    '''
    store_movies(start_year=int(args.start_year), end_year=int(args.end_year),
                  limit=int(args.limit), basepath=args.basepath)

    '''