package com.mashedtomatoes.media;

import com.mashedtomatoes.http.RateRequest;
import com.mashedtomatoes.http.ReviewReportRequest;
import com.mashedtomatoes.rating.RatingService;
import com.mashedtomatoes.user.User;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class MediaAPIController {

    @Autowired private MediaService mediaService;
    @Autowired private RatingService ratingService;
    @Autowired private Environment env;

    @PostMapping("/api/media/{id}/rate")
    public String submitRating(
            @PathVariable long id,
            @Valid @RequestBody RateRequest rateRequest,
            HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        Media media = mediaService.getMediaById(id);
        if (session == null) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return env.getProperty("user.notLoggedIn");
        } else if (media == null) {
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("media.notFound");
        }
        User user = (User) session.getAttribute("User");

        boolean isSubmitted =  ratingService.submitRating(
                media, user, rateRequest.getRating(), rateRequest.getReview());
        if(!isSubmitted){
            response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
            return env.getProperty("user.alreadySubmittedRating");
        }
        response.setStatus(HttpStatus.SC_OK);
        return "";
    }

    @PatchMapping("/api/media/{id}/rate/update/{ratingID}")
    public String updateRating(
            @PathVariable long id,
            @PathVariable int ratingID,
            @Valid @RequestBody RateRequest rateRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession httpSession = request.getSession(false);
        Media media = mediaService.getMediaById(id);
        if(httpSession == null){
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            return env.getProperty("user.notLoggedIn");
        }
        else if(media == null){
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("media.notFound");
        }
        User user = (User)httpSession.getAttribute("User");
        boolean hasUpdated = ratingService.updateRating(media, user, rateRequest.getRating() , rateRequest.getReview());
        if(!hasUpdated){
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return env.getProperty("user.hasNotSubmittedRating");
        }
        response.setStatus(HttpStatus.SC_OK);
        return "";
    }

    @DeleteMapping("/api/media/{id}/rate/delete/{ratingID}")
    public String deleteRating(
            @PathVariable long id,
            @PathVariable int ratingID,
            HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession httpSession = request.getSession(false);
        Media media = mediaService.getMediaById(id);
        boolean ratingExists = ratingService.ratingExistsById(ratingID);
        if (httpSession == null) {
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            return env.getProperty("user.notLoggedIn");
        }
        else if (media == null) {
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("media.notFound");
        }
        else if(ratingExists == false){
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("rating.notFound");
        }
        User user = (User) httpSession.getAttribute("User");
        boolean deleted = ratingService.deleteRating(media, user, ratingID);
        if (!deleted) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return env.getProperty("rating.notAuthToDelete");
        }
        mediaService.updateMedia(media);
        response.setStatus(HttpStatus.SC_OK);
        return "";
    }

    @PostMapping("/api/media/{id}/rate/report/{ratingID}")
    public String reportRating(
            @PathVariable long id,
            @PathVariable int ratingID,
            @Valid @RequestBody ReviewReportRequest reviewReportRequest,
            HttpServletRequest request,
            HttpServletResponse response){
        HttpSession httpSession = request.getSession(false);
        Media media = mediaService.getMediaById(id);
        boolean ratingExists = ratingService.ratingExistsById(ratingID);
        if(httpSession == null){
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            return env.getProperty("user.notLoggedIn");
        }
        else if(media == null){
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("media.notFound");
        }
        else if(ratingExists == false){
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            return env.getProperty("rating.notFound");
        }
        boolean wasReported = ratingService.reportRating(ratingID,reviewReportRequest.getReason());
        //Check if the rating was reported
        if(!wasReported){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return env.getProperty("ratingReport.alreadyExists");
        }
        response.setStatus(HttpStatus.SC_OK);
        return "";

    }
}
