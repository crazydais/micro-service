package com.example.webservice.controller;

import com.example.data.entity.Greeting;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {
  
  private static final Log LOG = LogFactory.getLog(GreetingController.class);

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  @ResponseBody
  public Greeting greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
    LOG.info( String.join(" ", "Greeting Called with the name", name) );
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}
