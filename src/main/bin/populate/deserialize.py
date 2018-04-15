import dateutil.parser

from . import models

def slugify(s):
    return s.lower().replace(' ', '-')

def json_to_genres(node):
    return list(map(lambda x: x['name'].upper(), node))

def json_to_production_company(node):
    try:
        return list(map(lambda x: x['name'], node))[0]
    except IndexError as e:
        return 'None'

def json_to_movie(node):
    return models.Movie(
        title=node['title'], slug=slugify(node['title']),
        description=node['overview'], backdrop_path=node['backdrop_path'],
        poster_path=node['poster_path'], 
        release_date=dateutil.parser.parse(node['release_date']),
        run_time=node['runtime'],  box_office=int(node['revenue']),
        genres=json_to_genres(node['genres']), budget=int(node['budget']),
        production_company=json_to_production_company(node['production_companies'])
    )

def json_to_media_crew_member(node):
    return models.MediaCrewMember(
        job=node['job'], department=node['department'],
        talent_id=node['id']
    )

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
    if 'birthday' in node:
        dateutil.parser.parse(node['birthday'])

    return models.Celebrity(
        biography=getattr(node, 'biography', 'None'),
        birthday=birthday,
        birthplace=getattr(node, 'place_of_birth', 'Unknown'),
        name=node['name'],
        profile_path=getattr(node, 'profile_path', None)
    )

def json_to_api_movie_ids(node, limit):
    ids = []
    for i, movie_node in enumerate(node['results'], 0):
        if i == limit:
            break
        ids.append(movie_node['id'])
    return ids
