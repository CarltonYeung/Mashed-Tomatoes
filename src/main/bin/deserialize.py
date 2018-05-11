import datetime
import re

import dateutil.parser

import models


def json_to_genres(node):
    return list(map(lambda x: x['name'].upper(), node))


def json_to_company(node):
    try:
        return list(map(lambda x: x['name'], node))[0]
    except IndexError as e:
        return 'None'


def json_to_creator_id(node):
    try:
        return list(map(lambda x: x['id'], node))[0]
    except IndexError as e:
        return 'None'


def json_to_movie(node):
    run_time = node.get('runtime', None)
    if not run_time:
        run_time = 0

    title = node.get('title', None)
    if not title:
        title = node.get('original_title', None)
        if not title:
            title = 'Unknown'

    release_date = node.get('release_date', None)
    if release_date:
        release_date = dateutil.parser.parse(release_date)

    return models.Movie(title=title, description=node.get('overview', 'N/A'),
                        poster_path=node.get('poster_path', None),
                        release_date=release_date,
                        run_time=run_time, box_office=int(
            node.get('revenue', 0)),
                        genres=json_to_genres(node.get('genres', [])), budget=int(node.get('budget', 0)),
                        production_company=json_to_company(
                            node.get('production_companies', []))
                        )


def json_to_tvshow(node):
    start_date = node.get('first_air_date', None)
    if start_date:
        start_date = dateutil.parser.parse(start_date)

    end_date = node.get('last_air_date', None)
    if end_date:
        end_date = dateutil.parser.parse(end_date)

    season_numbers = map(lambda season: int(season['season_number']), node['seasons'])

    episode_run_time = node.get('episode_run_time', [])
    if len(episode_run_time) == 0:
        episode_run_time = 0
    else:
        episode_run_time = episode_run_time[0]

    return models.TVShow(title=node['original_name'], description=node['overview'],
                         poster_path=node['poster_path'],
                         start_date=start_date, end_date=end_date,
                         episode_run_time=episode_run_time,
                         genres=json_to_genres(node['genres']),
                         production_company=json_to_company(
                             node['production_companies']),
                         network_company=json_to_company(node['networks']),
                         seasons=node['number_of_seasons'],
                         episodes=node['number_of_episodes'],
                         api_creator_id=json_to_creator_id(node['created_by']),
                         season_numbers=season_numbers
                         )


def json_to_media_crew_member(node):
    return models.MediaCrewMember(job=node['job'],
                                  department=node['department'],
                                  talent_id=node['id'])


def json_to_media_cast_member(node):
    return models.MediaCastMember(
        character=models.Character(
            name=node['character'],
            cast_order=int(node['order']),
        ),
        talent_id=node['id']
    )


def json_to_media_credits(node):
    crew = []
    for crew_node in node.get('crew', []):
        crew.append(json_to_media_crew_member(crew_node))

    cast = []
    for cast_node in node.get('cast', []):
        cast.append(json_to_media_cast_member(cast_node))

    return models.MediaCredits(crew=crew, cast=cast)


def json_to_celebrity(node):
    birthday = None
    if 'birthday' in node and node['birthday']:
        birthday = dateutil.parser.parse(node['birthday'])

    return models.Celebrity(
        biography=node.get('biography', None),
        birthday=birthday,
        birthplace=node.get('place_of_birth', 'Unknown'),
        name=node.get('name', 'Unknown'),
        profile_path=node.get('profile_path', None)
    )


def json_to_api_media_ids(node, limit):
    ids = []
    for i, movie_node in enumerate(node['results'], 0):
        if i == limit:
            break
        ids.append(movie_node['id'])
    return ids


def json_to_air_dates(node):
    episodes = node['episodes']
    air_dates = []
    for episode_node in episodes:
        air_date = episode_node.get('air_date', None)
        if air_date:
            air_dates.append(dateutil.parser.parse(air_date))
    return air_dates


def json_to_movie_id(node, title, year_date):
    results = map(lambda r: models.SearchMovie(
        id=int(r['id']),
        title=r['title'],
        release_date=dateutil.parser.parse(r['release_date'])
    ), node['results'])

    def trim_str(s):
        return re.sub(r'\W+', '', s.lower())

    results = filter(lambda r: trim_str(r.title) == trim_str(title), results)
    results = filter(lambda r: r.release_date - year_date < datetime.timedelta(days=400), results)
    results = map(lambda r: (r.id, year_date.year), results)
    results = list(results)
    return results[0]
