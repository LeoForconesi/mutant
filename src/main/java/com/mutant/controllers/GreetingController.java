package com.mutant.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.mutant.domain.Greeting;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static Logger logger =  Logger.getGlobal();

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
  }

  @RequestMapping(value="/binaryGap", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
  public Integer binaryGap(@RequestParam(value="number") Integer number) {
    if (number != null) {
      number >>>= Integer.numberOfTrailingZeros(number); // descartar todos los 0 al final del nro binario
      int steps = 0;
      while ((number & (number+ 1)) != 0) {
        number |= number>>> 1;
        steps++;
      }
      logger.log(Level.ALL, "Binary gap of " + number + " = " + steps);
      return steps;
    } else {
      return 0;
    }

  }
}
/*
@RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method.
If the name parameter is absent in the request, the defaultValue of "World" is used.

The implementation of the method body creates and returns a new com.mutant.Domain.Greeting object with id
 and content attributes based on the next value from the counter, and formats the given name
 by using the greeting template.

 A key difference between a traditional MVC controller and the RESTful web service controller above
 is the way that the HTTP response body is created. Rather than relying on a view technology to perform server-side rendering
  of the greeting data to HTML, this RESTful web service controller simply populates and returns a com.mutant.Domain.Greeting object.
  The object data will be written directly to the HTTP response as JSON

  This code uses Spring 4’s new @RestController annotation, which marks the class as a controller where every
  method returns a domain object instead of a view. It’s shorthand for @Controller and @ResponseBody rolled together.
* */