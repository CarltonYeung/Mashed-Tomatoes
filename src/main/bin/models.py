from collections import namedtuple

Movie = namedtuple('Movie', ['title', 'description',
                             'poster_path', 'release_date', 'run_time',
                             'box_office', 'genres', 'production_company',
                             'budget'])

TVShow = namedtuple('TVShow', ['title', 'description', 'poster_path',
                               'production_company', 'start_date', 'end_date',
                               'episode_run_time', 'seasons', 'episodes',
                               'season_numbers',
                               'genres', 'network_company', 'api_creator_id'])

MediaCrewMember = namedtuple('MediaCrewMember', ['job', 'department',
                                                 'talent_id'])

MediaCastMember = namedtuple('MediaCastMember', ['character', 'talent_id'])

MediaCredits = namedtuple('MediaCredits', ['cast', 'crew'])

Celebrity = namedtuple('Celebrity', ['biography', 'birthday',
                                     'birthplace', 'name', 'profile_path'])

Character = namedtuple('Character', ['cast_order', 'name'])

SearchMovie = namedtuple('SearchMovie', ['id', 'title', 'release_date'])

OscarWinnerSet = namedtuple('OscarWinnerSet',
                            ['year', 'best_picture_id', 'best_cinema_id', 'best_editing_id', 'best_doc_id',
                             'best_animated_id'])
