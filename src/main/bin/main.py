from collections import namedtuple

import requests
import mysql.connector

import db
import net
from deserialize import json_to_movie, json_to_genres

# private Set<Celebrity> writtenBy = new HashSet<>();
# private Set<Celebrity> celebrities = new HashSet<>();
# private Set<Genre> genres = new HashSet<>();

movie = net.get_movie(550)
db.save_movie(movie)
db.close()

print (net.get_movie_credits(550))