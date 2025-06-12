package de.der_e_coach.minimal_service.service.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfiguration {
  //#region Configuration beans -----------------------------------------------
  @Bean
  public RequestInterceptor requestInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate requestTemplate) {
        String jwtToken = "Bearer " + getJwtToken();
        if (jwtToken != null) {
          requestTemplate.header("Authorization", jwtToken);
        }        
      }

      private String getJwtToken() {
        System.out.println("in interceptor");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder
          .getRequestAttributes();
        if (requestAttributes != null) {
          HttpServletRequest request = requestAttributes.getRequest();
          String authorizationHeader = request.getHeader("Authorization");
          System.out.println("authorization header is " + authorizationHeader);
          if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // get rid of "Bearer " 
          }
        }
        return null; 
      }
    };
  }
  //#endregion
}
