package com.mashedtomatoes.shell;

import com.mashedtomatoes.model.celebrity.Celebrity;
import com.mashedtomatoes.model.celebrity.Character;
import com.mashedtomatoes.model.media.Movie;
import com.mashedtomatoes.shell.deserializer.CharacterTalentId;
import com.mashedtomatoes.shell.deserializer.SerializationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ShellComponent
public class PopulateComponent {

    @Autowired
    DatabaseManager databaseManager;

    @Autowired
    HttpManager httpManager;

    @Autowired
    SerializationManager serializationManager;


    @ShellMethod("Populate database with data from The Movie Database API")
    public void populate(@ShellOption(defaultValue = "", help = "Path to download images, if not given, images won't be downloaded") String imgBasePath) {
        if (imgBasePath.equals("")) {
            addMovie(550, null);
        } else {
            addMovie(550, imgBasePath);
        }
    }

    public Celebrity addCelebrity(Integer celebrityId, String imgBasePath) {
        Celebrity celebrity = httpManager.getObject(httpManager.getPersonUrl(celebrityId), Celebrity.class);
        if (celebrity == null || celebrity.getName() == null) {
            return null;
        }

        Optional<Celebrity> dbCelebrity = databaseManager.getCelebrityRepository()
                .findFirstByName(celebrity.getName());

        if (dbCelebrity.isPresent()) {
            System.out.println("Celebrity already exists: " + dbCelebrity.get().getName());
            return dbCelebrity.get();
        }

        if (imgBasePath != null) {
            httpManager.downloadBinary(httpManager.getProfileImgUrl(celebrity.getProfilePath()), imgBasePath, null);
            System.out.println("Downloaded profile image for: " + celebrity.getName());
        }

        celebrity = databaseManager.getCelebrityRepository().save(celebrity);
        System.out.println("Saved celebrity: " + celebrity.getName());
        return celebrity;
    }

    public Set<Celebrity> addCelebrities(Integer movieId, String imgBasePath) {
        Set<Celebrity> celebrities = new HashSet<>();
        for (CharacterTalentId characterTalentId : httpManager.getCharacterTalentIdSet(movieId)
                .getCharacterTalentIds()) {
            Celebrity celebrity = addCelebrity(characterTalentId.getTalentId(), imgBasePath);
            Character character = characterTalentId.getCharacter();
            if (celebrity != null) {
                character.setPlayedBy(celebrity);
                databaseManager.getCharacterRepository().save(character);
                System.out.println("Saved character: " + character.getName());
                celebrities.add(celebrity);
            }
        }
        return celebrities;

    }

    public Movie addMovie(Integer movieId, String imgBasePath) {
        Movie m = httpManager.getMovie(movieId);

        if (imgBasePath != null) {
            String[] urls = {
                    httpManager.getBackdropImgUrl(m.getBackdropPath()),
                    httpManager.getPosterImgUrl(m.getPosterPath())
            };
            for (String url : urls) {
                httpManager.downloadBinary(url, imgBasePath, null);
            }
            System.out.println("Downloaded images for: " + m.getTitle());
        }

        m = databaseManager.getMovieRepository().save(m);

        m.setCelebrities(addCelebrities(movieId, imgBasePath));

        System.out.println("Saved movie: " + m.getTitle());
        m = databaseManager.getMovieRepository().save(m);

        return m;
    }

}
