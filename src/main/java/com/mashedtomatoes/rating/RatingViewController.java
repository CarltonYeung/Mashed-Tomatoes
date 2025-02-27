package com.mashedtomatoes.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RatingViewController {

    @Autowired private Environment env;
    @Autowired private RatingService ratingService;

    @Value("${mt.files.uri}")
    private String filesUri = "/files";

    private static final Map<String, String> reviewFilter = new HashMap<String, String>();
    static{
        reviewFilter.put("Latest", "latest");
        reviewFilter.put("All", "all");
    }

    @GetMapping("/review")
    public String getReviews(@RequestParam(required = false, defaultValue = "all", value = "filter") String filter,
                             @RequestParam(required = false, value = "page") Integer pageInt,
                             Model m){
        if(!filter.equals("all") && !filter.equals("latest")){
            filter = "all";
            pageInt = 0;
        }
        int page;
        if(pageInt == null || pageInt.intValue() < 0){
            page = 0;
        }
        else{
            page = pageInt.intValue();
        }
        int limit = Integer.parseInt(env.getProperty("mt.filteredPage.limit"));
        Iterable<CriticRating> criticRatings =  ratingService.getReviews(page, limit, filter);
        List<CriticRatingViewModel> criticRatingViewModels = new ArrayList<CriticRatingViewModel>();
        for (Iterator criticRatingIterator = criticRatings.iterator(); criticRatingIterator.hasNext();) {
            criticRatingViewModels.add(new CriticRatingViewModel((CriticRating) criticRatingIterator.next(), filesUri));
        }
        m.addAttribute("page", page);
        m.addAttribute("filter", filter);
        m.addAttribute("reviews", criticRatingViewModels);
        m.addAttribute("reviewFilters", reviewFilter);
        return "ratings/reviewfilter";
    }
}
