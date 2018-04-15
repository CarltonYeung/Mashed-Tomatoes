import os
import requests
import time

from .deserialize import json_to_movie, json_to_media_credits, \
json_to_celebrity, json_to_api_movie_ids

API_BASE_URL = 'https://api.themoviedb.org/3'
API_KEY = '340eea09f9407d59cc1ef319b7c6f072'
IMAGE_BASE_URL = 'https://image.tmdb.org/t/p'
BACKDROP_SIZE = '/w1280'
POSTER_SIZE = '/w780'
PROFILE_SIZE = '/w185'
RATE_LIMIT = 40
RATE_LIMIT_DELAY = 10

__request_ct = 0


def get_movie_url(movie_id):
    return API_BASE_URL + "/movie/" + str(movie_id)


def get_movie_credits_url(movie_id):
    return get_movie_url(movie_id) + "/credits"


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
        'year': year
    })
    return json_to_api_movie_ids(r.json(), limit)


def download_file(url, basepath):
    r = requests.get(url, stream=True)
    filename = os.path.join(basepath, url.split('/')[-1])
    with open(filename, 'wb+') as fd:
        for chunk in r.iter_content(chunk_size=128):
            fd.write(chunk)
