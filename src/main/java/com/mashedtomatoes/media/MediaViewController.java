package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MediaViewController {

    @Autowired MediaService mediaService;

    @GetMapping("/media/{id}")
    public String getMedia(@PathVariable long id) {
        Media media = mediaService.getMediaById(id);
        if (media == null) {
            return "error/404";
        }

        if (media instanceof Movie) {
            return String.format("redirect:/movie/%d", id);
        } else {
            return String.format("redirect:/tv/%d", id);
        }
    }
}
