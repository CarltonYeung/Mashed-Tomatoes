package com.mashedtomatoes.user;

import com.mashedtomatoes.http.CriticApplicationRequest;
import com.mashedtomatoes.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CriticApplicationService {
  @Autowired private CriticApplicationRepository criticApplicationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserService userService;
  @Autowired private MailService mailService;
  @Autowired private Environment env;

  public void submitCriticApplication(Audience applicant, CriticApplicationRequest request) throws Exception {
    if (criticApplicationRepository.existsByApplicant_Id(applicant.getId())) {
      throw new Exception(env.getProperty("user.duplicateCriticApplication"));
    }

    CriticApplication app = new CriticApplication();
    app.setApplicant((Audience) userRepository.findFirstById(applicant.getId()).get());
    app.setFirstName(request.getFirstName());
    app.setLastName(request.getLastName());
    app.setBody(request.getBody());
    criticApplicationRepository.save(app);
  }

  public CriticApplication getApplicationByApplicantId(long applicantId) throws NoSuchElementException {
    Optional<CriticApplication> optional = criticApplicationRepository.findFirstByApplicant_Id(applicantId);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  private void deleteApplication(CriticApplication app) {
    Audience applicant = app.getApplicant();
    applicant.setCriticApplication(null);
    userRepository.save(applicant);
    criticApplicationRepository.delete(app);
  }

  public void accept(long userId) throws NoSuchElementException {
    CriticApplication app = getApplicationByApplicantId(userId);
    Audience applicant = app.getApplicant();
    Critic critic = audienceToCritic(applicant, app.getFirstName(), app.getLastName());
    deleteApplication(app);
    userService.delete(userId);
    userRepository.save(critic);
    mailService.sendCriticApplicationStatusEmail(applicant.getCredentials().getEmail(), true);
  }

  private Critic audienceToCritic(Audience audience, String firstName, String lastName) {
    Critic critic = new Critic(audience.getDisplayName(), firstName, lastName);
    critic.getCredentials().setEmail(audience.getCredentials().getEmail());
    critic.getCredentials().setPassword(audience.getCredentials().getPassword());
    critic.getVerification().setUser(critic);
    critic.getVerification().setVerified(audience.getVerification().isVerified());
    critic.getVerification().setVerificationKey(audience.getVerification().getVerificationKey());
    critic.getVerification().setExpiration(audience.getVerification().getExpiration());
    critic.setWantToSee(audience.getWantToSee());
    critic.setNotInterested(audience.getNotInterested());
    critic.setProfileViews(audience.getProfileViews());
    critic.setPublicProfile(audience.isPublicProfile());
    if (audience.getReport() != null) {
      UserReport report = new UserReport();
      report.setUser(critic);
      report.setReason(audience.getReport().getReason());
      critic.setReport(report);
    }
    return critic;
  }

  public void reject(long userId) throws NoSuchElementException {
    CriticApplication app = getApplicationByApplicantId(userId);
    deleteApplication(app);
    Audience applicant = app.getApplicant();
    mailService.sendCriticApplicationStatusEmail(applicant.getCredentials().getEmail(), false);
  }

  public Iterable<CriticApplication> findAll() {
    return criticApplicationRepository.findAll();
  }
}
