package com.mashedtomatoes.rating;

import com.mashedtomatoes.user.Critic;
import com.mashedtomatoes.user.CriticViewModel;
import com.mashedtomatoes.util.Util;

public class CriticRatingViewModel extends RatingViewModel {
    public CriticRatingViewModel(CriticRating base, String fileUri){
        super(base, fileUri);
        setAuthor(new CriticViewModel((Critic) getAuthor(), fileUri));
        getAuthor().getRatings().stream().forEach(
                rating -> {
                    final String path = rating.getMedia().getPosterPath();
                    rating.getMedia().setPosterPath(Util.resolveFilesUrl(fileUri, path));
                }
        );
    }
}
