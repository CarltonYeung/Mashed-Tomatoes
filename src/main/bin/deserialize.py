import models
import dateutil.parser

def slugify(s):
    return s.lower().replace(' ', '-')

def json_to_genres(node):
    return list(map(lambda x: x['name'].upper(), node))

def json_to_production_company(node):
    return list(map(lambda x: x['name'], node))[0]

def json_to_movie(node):
    return models.Movie(
        title=node['title'], slug=slugify(node['title']),
        description=node['overview'], backdrop_path=node['backdrop_path'],
        poster_path=node['poster_path'], 
        release_date=dateutil.parser.parse(node['release_date']),
        run_time=node['runtime'],  box_office=int(node['revenue']),
        genres=json_to_genres(node['genres']),
        production_company=json_to_production_company(node['production_companies'])
    )

def json_to_media_crew_member(node):
    return models.MediaCrewMember(
        job=node['job'], department=node['department'],
        talent_id=node['id']
    )

def json_to_media_cast_member(node):
    return models.MediaCastMember(
        character_name=node['character'],
        character_order=int(node['order']),
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