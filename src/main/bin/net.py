import requests

from deserialize import json_to_movie, json_to_media_credits

API_BASE_URL = 'https://api.themoviedb.org/3'
API_KEY = '340eea09f9407d59cc1ef319b7c6f072'
IMAGE_BASE_URL = 'https://image.tmdb.org/t/p'
BACKDROP_SIZE = '/w1280'
POSTER_SIZE = '/w780'
PROFILE_SIZE = '/w185'
RATE_LIMIT = 40
RATE_LIMIT_DELAY = 10

def get_movie_url(movie_id):
    return API_BASE_URL + "/movie/" + str(movie_id);

def get_movie_credits_url(movie_id):
    return get_movie_url(movie_id) + "/credits";

def get_person_url(person_id):
    return API_BASE_URL + "/person/" + person_id

def get_image_url(uri, size):
    return IMAGE_BASE_URL + size + uri

def get_backdrop_img_url(uri):
    return get_image_url(uri, BACKDROP_SIZE)

def get_poster_img_url(uri):
    return get_image_url(uri, POSTER_SIZE)

def get_profile_img_url(uri):
    return get_image_url(uri, PROFILE_SIZE)

def get_movie(movie_id):
    r = requests.get(get_movie_url(movie_id), params={'api_key': API_KEY})
    return json_to_movie(r.json())

def get_movie_credits(movie_id):
    r = requests.get(get_movie_credits_url(movie_id), params={'api_key': API_KEY})
    return json_to_media_credits(r.json())

'''
public class HttpManager {

  public MediaCredits getMediaCredits(Integer movieId) {
    return getObject(getMovieCreditsUrl(movieId), MediaCredits.class);
  }

  public void delayRequest() {
    if (requestCount % RateLimit == 0) {
      try {
        TimeUnit.SECONDS.sleep(RateLimitDelay);
      } catch (InterruptedException ex) {}
    }
    requestCount++;
  }

  public JsonNode getTopMoviesByYear(Integer year) {
    delayRequest();
    try {
      String url = ApiBaseURL + "/discover/movie";
      return Unirest.get(url)
          .header("accept", "application/json")
          .queryString("api_key", apiKey)
          .queryString("language", "en-US")
          .queryString("sort_by", "popularity.desc")
          .queryString("year", year)
          .asJson()
          .getBody();
    } catch (UnirestException ex) {
      System.err.println("Failed to get movies for year: " + year);
      return null;
    }
  }

  public <T> T getObject(String url, Class<? extends T> klass) {
    delayRequest();
    try {
      return Unirest.get(url)
          .header("accept", "application/json")
          .queryString("api_key", apiKey)
          .asObject(klass).getBody();
    } catch (UnirestException ex) {
      System.err.println("Failed to get: " + url);
      ex.printStackTrace();
      return null;
    }

  }

  public void downloadBinary(String url, String basepath, String filename) {
    delayRequest();
    try {
      HttpResponse<InputStream> response = Unirest.get(url).asBinary();
      if (filename == null) {
        String[] parts = url.split("/");
        filename = "/" + parts[parts.length - 1];
      }
      Path path = Paths.get(basepath, filename);
      File f = new File(path.toString());
      FileCopyUtils.copy(response.getBody(), new FileOutputStream(f));
    } catch (UnirestException ex) {
      System.err.println(ex);
    } catch (FileNotFoundException ex) {
      System.err.println(ex);
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

}
'''