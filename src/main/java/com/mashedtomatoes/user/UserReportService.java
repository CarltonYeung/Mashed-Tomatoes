package com.mashedtomatoes.user;

import com.mashedtomatoes.http.UserReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserReportService {
  @Autowired private UserReportRepository userReportRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private Environment env;

  public void addReport(UserReportRequest request) throws Exception {
    if (userReportRepository.existsByUser_Id(request.getUserId())) {
      throw new Exception(env.getProperty("userReport.alreadyExists"));
    }

    Optional<User> optional = userRepository.findFirstById(request.getUserId());
    optional.orElseThrow(NoSuchElementException::new);
    User user = optional.get();

    UserReport report = new UserReport();
    report.setUser(user);
    report.setReason(request.getReason());
    userReportRepository.save(report);
  }

  public void removeReport(long userId) throws NoSuchElementException {
    Optional<UserReport> optionalReport = userReportRepository.findFirstByUser_Id(userId);
    optionalReport.orElseThrow(NoSuchElementException::new);
    UserReport report = optionalReport.get();

    Optional<User> optionalUser = userRepository.findFirstById(userId);
    optionalUser.orElseThrow(NoSuchElementException::new);
    User user = optionalUser.get();

    user.setReport(null);
    userRepository.save(user);

    userReportRepository.delete(report);
  }

  public Iterable<UserReport> findAll() {
    return userReportRepository.findAll();
  }
}
