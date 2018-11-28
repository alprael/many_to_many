package edu.cnm.deepdive.many_to_many.view;

import java.net.URI;
import java.util.Date;

public interface BaseProject {

  long getId();

  Date getCreated();

  String getName();

  URI getHref();
}
