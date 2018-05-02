package com.mashedtomatoes.user;

import com.mashedtomatoes.http.CriticApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CriticApplicationService {
  @Autowired private CriticApplicationRepository criticApplicationRepository;
  @Autowired private UserRepository userRepository;
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

  public void accept(long id) throws NoSuchElementException {
    CriticApplication app = getApplicationByApplicantId(id);
    Audience applicant = app.getApplicant();
    applicant.setType(UserType.CRITIC);
    deleteApplication(app);
  }

  public void reject(long id) throws NoSuchElementException {
    CriticApplication app = getApplicationByApplicantId(id);
    deleteApplication(app);
  }
}
