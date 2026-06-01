package com.umc.umcmission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UmcMissionApplication {

  public static void main(String[] args) {
    SpringApplication.run(UmcMissionApplication.class, args);
  }

}
