package edu.cnm.deepdive.many_to_many;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @Scheduled(fixedRate = 5000)
  public void currentTime() {
    log.info(new Date().toString());
  }

}
