package com.mashedtomatoes.rating;

import com.mashedtomatoes.util.Util;

public class RatingViewModel extends Rating {
    public RatingViewModel(Rating base, String fileUri){
        super.setId(base.getId());
        super.setScore(base.getScore());
        super.setReview(base.getReview());
        super.setMedia(base.getMedia());
        super.setAuthor(base.getAuthor());
        super.setCreated(base.getCreated());
        super.setUpdated(base.getUpdated());
        getMedia().setPosterPath(Util.resolveFilesUrl(fileUri, getMedia().getPosterPath()));
        getAuthor().getRatings().stream().forEach(
                rating -> {
                    final String path = rating.getMedia().getPosterPath();
                    rating.getMedia().setPosterPath(Util.resolveFilesUrl(fileUri, path));
                }
        );

    }

}
