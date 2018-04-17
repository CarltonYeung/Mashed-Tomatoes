from collections import namedtuple

Movie = namedtuple('Movie', ['title', 'slug', 'description',
    'backdrop_path', 'poster_path', 'release_date', 'run_time', 'box_office', 
    'genres', 'production_company', 'budget'
])

MediaCrewMember = namedtuple('MediaCrewMember', ['job', 'department', 'talent_id'])

MediaCastMember = namedtuple('MediaCastMember', ['character', 'talent_id'])

MediaCredits = namedtuple('MediaCredits', ['cast', 'crew'])

Celebrity = namedtuple('Celebrity', ['biography', 'birthday', 'birthplace', 'name', 'profile_path'])

Character = namedtuple('Character', ['cast_order', 'name'])