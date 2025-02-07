import os
import time

import requests

from deserialize import *

API_BASE_URL = 'https://api.themoviedb.org/3'
IMAGE_BASE_URL = 'https://image.tmdb.org/t/p'
BACKDROP_SIZE = '/w1280'
POSTER_SIZE = '/w780'
PROFILE_SIZE = '/w185'
RATE_LIMIT = 40
RATE_LIMIT_DELAY = 10
API_KEY = os.environ['MOVIEDB_API_KEY']

__request_ct = 0


def get_movie_url(movie_id):
    return API_BASE_URL + "/movie/" + str(movie_id)


def get_movie_credits_url(movie_id):
    return get_movie_url(movie_id) + "/credits"


def get_movies_now_playing_url():
    return API_BASE_URL + "/movie/now_playing"


def get_movies_coming_soon_url():
    return API_BASE_URL + "/movie/upcoming"


def get_search_movie_url():
    return API_BASE_URL + "/search/movie"


def get_tvshow_url(tvshow_id):
    return API_BASE_URL + "/tv/" + str(tvshow_id)


def get_tvshow_credits_url(tvshow_id):
    return get_tvshow_url(tvshow_id) + "/credits"


def get_tvshow_season_url(tvshow_id, season_number):
    return get_tvshow_url(tvshow_id) + "/season/" + str(season_number)

def get_tvshows_now_airing_url():
    return API_BASE_URL + "/tv/on_the_air"

def get_person_url(person_id):
    return API_BASE_URL + "/person/" + str(person_id)


def get_image_url(uri, size):
    return IMAGE_BASE_URL + size + uri


def get_backdrop_img_url(uri):
    return get_image_url(uri, BACKDROP_SIZE)


def get_poster_img_url(uri):
    return get_image_url(uri, POSTER_SIZE)


def get_profile_img_url(uri):
    return get_image_url(uri, PROFILE_SIZE)


def delay_request():
    global __request_ct
    if __request_ct % RATE_LIMIT == 0:
        time.sleep(RATE_LIMIT_DELAY)
    __request_ct += 1


def get_movie(movie_id):
    delay_request()
    r = requests.get(get_movie_url(movie_id), params={'api_key': API_KEY})
    return json_to_movie(r.json())


def get_movie_credits(movie_id):
    delay_request()
    r = requests.get(get_movie_credits_url(
        movie_id), params={'api_key': API_KEY})
    return json_to_media_credits(r.json())


def get_search_movie(title, year):
    delay_request()
    r = requests.get(get_search_movie_url(), params={'api_key': API_KEY, 'query': title})
    return json_to_movie_id(r.json(), title, year)


def get_movies_now_playing(limit, page):
    delay_request()
    r = requests.get(get_movies_now_playing_url(), params={'api_key': API_KEY, 'language': 'en-US', 'page':page})
    return json_to_api_media_ids(r.json(), limit)


def get_movies_coming_soon(page, limit):
    delay_request()
    r = requests.get(get_movies_coming_soon_url(), params={'api_key': API_KEY, 'language': 'en-US', 'page':page})
    return json_to_api_media_ids(r.json(), limit)


def get_tvshow(tvshow_id):
    delay_request()
    r = requests.get(get_tvshow_url(tvshow_id), params={'api_key': API_KEY})
    return json_to_tvshow(r.json())


def get_tvshow_credits(tvshow_id):
    delay_request()
    r = requests.get(get_tvshow_credits_url(tvshow_id), params={'api_key': API_KEY})
    return json_to_media_credits(r.json())


def get_tvshow_season_air_dates(tvshow_id, season_number):
    delay_request()
    r = requests.get(get_tvshow_season_url(tvshow_id, season_number), params={'api_key': API_KEY})
    return json_to_air_dates(r.json())


def get_tvshows_now_airing(page, limit):
    delay_request()
    r = requests.get(get_tvshows_now_airing_url(), params={'api_key': API_KEY, 'language': 'en-US', 'page':page})
    return json_to_api_media_ids(r.json(), limit)

def get_celebrity(celebrity_id):
    delay_request()
    r = requests.get(get_person_url(celebrity_id), params={'api_key': API_KEY})
    return json_to_celebrity(r.json())


def get_top_movies(year, limit):
    delay_request()
    url = API_BASE_URL + '/discover/movie'
    r = requests.get(url, params={
        'api_key': API_KEY,
        'language': 'en-US',
        'sort_by': 'popularity.desc',
        'primary_release_year': year
    })
    return json_to_api_media_ids(r.json(), limit)


def get_top_tvshows(year, limit):
    delay_request()
    url = API_BASE_URL + '/discover/tv'
    r = requests.get(url, params={
        'api_key': API_KEY,
        'language': 'en-US',
        'sort_by': 'popularity.desc',
        'first_air_date_year': year,
        'timezone': 'America/New_York'
    })
    return json_to_api_media_ids(r.json(), limit)


def download_file(url, basepath):
    r = requests.get(url, stream=True)
    filename = os.path.join(basepath, url.split('/')[-1])
    with open(filename, 'wb+') as fd:
        for chunk in r.iter_content(chunk_size=128):
            fd.write(chunk)
