from collections import namedtuple

Movie = namedtuple('Movie', ['title', 'slug', 'description',
    'backdrop_path', 'poster_path', 'release_date', 'run_time', 'box_office', 
    'genres', 'production_company'
])

MediaCrewMember = namedtuple('MediaCrewMember', ['job', 'department', 'talent_id'])

MediaCastMember = namedtuple('MediaCastMember', ['character_name', 'character_order', 'talent_id'])

MediaCredits = namedtuple('MediaCredits', ['cast', 'crew'])