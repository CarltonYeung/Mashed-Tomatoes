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

def json_to_runtime(node):
    try:
        return node[0]
    except IndexError as e:
        return 23 # default 23mins


def json_to_movie(node):
    return models.Movie(title=node['original_title'], description=node['overview'],
                        poster_path=node['poster_path'],
                        release_date=dateutil.parser.parse(
                            node['release_date']),
                        run_time=node['runtime'],  box_office=int(
                            node['revenue']),
                        genres=json_to_genres(node['genres']), budget=int(node['budget']),
                        production_company=json_to_company(
                            node['production_companies'])
                        )


def json_to_tvshow(node):
    return models.TVShow(title=node['original_name'], description=node['overview'],
                         poster_path=node['poster_path'],
                         start_date=node['first_air_date'],
                         end_date=node['last_air_date'],
                         episode_run_time=json_to_runtime(node['episode_run_time']),
                         genres=json_to_genres(node['genres']),
                         production_company=json_to_company(
                             node['production_companies']),
                         network_company=json_to_company(node['networks']),
                         seasons=node['number_of_seasons'],
                         episodes=node['number_of_episodes'],
                         api_creator_id=json_to_creator_id(node['created_by'])
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
    for crew_node in node['crew']:
        crew.append(json_to_media_crew_member(crew_node))

    cast = []
    for cast_node in node['cast']:
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
        name=node.get('name', 'John Doe'),
        profile_path=node.get('profile_path', None)
    )


def json_to_api_movie_ids(node, limit):
    ids = []
    for i, movie_node in enumerate(node['results'], 0):
        if i == limit:
            break
        ids.append(movie_node['id'])
    return ids
