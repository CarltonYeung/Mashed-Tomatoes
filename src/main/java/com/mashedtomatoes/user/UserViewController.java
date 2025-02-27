package com.mashedtomatoes.user;

import com.mashedtomatoes.rating.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserViewController {
  @Autowired private UserService userService;
  @Autowired private UserReportService userReportService;
  @Autowired private ReviewReportService reviewReportService;
  @Autowired private CriticApplicationService criticApplicationService;
  @Autowired private Environment env;

  @Value("${mt.rank.upgrade.rate}")
  private int rankUpgradeRate = 10;

  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  private static final Map<String, String> criticFilter = new HashMap<String, String>();
  static{
    criticFilter.put("Top", "top");
    criticFilter.put("All", "all");
  }

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @GetMapping("/register")
  public String register() {
    return "user/register";
  }

  @GetMapping("/recover")
  public String recover() {
    return "user/recover";
  }

  @GetMapping("/resend")
  public String resend() {
    return "user/resend";
  }

  @GetMapping("/critic/apply")
  public String criticApply() {
  	return "user/criticapply";
  }

  @GetMapping("/user/me")
  public String user(HttpServletResponse response) {
    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return "error/401";
    }

    User user = (User) session.getAttribute("User");
    return String.format("redirect:/user/%d", user.getId());
  }

  @GetMapping("/user/{id}")
  public String user(@PathVariable long id, HttpServletResponse response, Model model) {
    User dbUser;
    try {
      dbUser = userService.getUserById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      model.addAttribute("notFound", true);
      return "user/user";
    }

    User sessionUser = null;
    HttpSession session = UserService.session();
    if (session != null) {
      sessionUser = (User) session.getAttribute("User");
    }

    boolean viewingOwnProfile = session != null && sessionUser.getId() == dbUser.getId();
    if (!viewingOwnProfile) {
      dbUser.setProfileViews(dbUser.getProfileViews() + 1);
      dbUser = userService.save(dbUser);
    }

    model.addAttribute("viewingOwnProfile", viewingOwnProfile);
    boolean isFollowing = false;
    if (session != null && !viewingOwnProfile) {
      for (User user: sessionUser.getFollowing()) {
        if (user.getId() == dbUser.getId()) {
          isFollowing = true;
          break;
        }
      }
    }

    model.addAttribute("isFollowing", isFollowing);
    if (dbUser.getType() == UserType.AUDIENCE) {
      model.addAttribute("user", new AudienceViewModel((Audience) dbUser, filesUri, rankUpgradeRate));
    } else if (dbUser.getType() == UserType.CRITIC) {
      model.addAttribute("user", new CriticViewModel((Critic) dbUser, filesUri));
    } else {
      model.addAttribute("user", new AdministratorViewModel((Administrator) dbUser, filesUri));
    }

    model.addAttribute("notFound", false);
    return "user/user";
  }

  @GetMapping("/user/admin")
  public String admin(HttpServletResponse response, Model model) {
    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return "error/401";
    }

    User user = (User) session.getAttribute("User");
    if (!(user instanceof Administrator)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return "error/401";
    }

    model.addAttribute("applications", criticApplicationService.findAll());
    model.addAttribute("userReports", userReportService.findAll());
    model.addAttribute("reviewReports", reviewReportService.findAll());

    return "user/admin";
  }

  @GetMapping("/critic")
  public String getCritics(@RequestParam(required = false, defaultValue = "all", value = "filter") String filter,
                           @RequestParam(required = false, value = "page") Integer pageInt,
                           Model m){
    if(!filter.equals("all") && !filter.equals("top")){
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
    Iterable<Critic> critics = userService.getCritics(page, limit, filter);
    List<CriticViewModel> criticViewModelList = new ArrayList<CriticViewModel>();
    for(Iterator criticsIterator = critics.iterator(); criticsIterator.hasNext();){
      criticViewModelList.add(new CriticViewModel((Critic) criticsIterator.next(),filesUri));
    }
    m.addAttribute("page", page);
    m.addAttribute("filter", filter);
    m.addAttribute("critics", criticViewModelList);
    m.addAttribute("criticFilters", criticFilter);
    return "user/criticfilter";
  }

  @GetMapping("/verify")
  public String verify(
          @RequestParam("email") String email,
          @RequestParam("key") String key,
          Model model) {

    model.addAttribute("success", userService.verifyEmail(email, key));
    return "user/verify";
  }
}
