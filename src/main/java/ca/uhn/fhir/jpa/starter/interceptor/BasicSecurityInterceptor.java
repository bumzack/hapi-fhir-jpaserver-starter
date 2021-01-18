// https://hapifhir.io/hapi-fhir/docs/security/introduction.html
// https://hapifhir.io/hapi-fhir/docs/security/authorization_interceptor.html

package ca.uhn.fhir.jpa.starter.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor
public class BasicSecurityInterceptor {
  private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(BasicSecurityInterceptor.class);

  private static final String USERNAME_PREFIX = "phr_";
  private static final String PASSWORD = "phr";

  /**
   * This interceptor implements HTTP Basic Auth, which specifies that
   * a username and password are provided in a header called Authorization.
   */
  @Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
  public boolean incomingRequestPostProcessed(RequestDetails theRequestDetails, HttpServletRequest theRequest, HttpServletResponse theResponse) throws AuthenticationException {
    final String authHeader = theRequest.getHeader("Authorization");
    ourLog.error("authHeader: " + authHeader);

    // The format of the header must be:
    // Authorization: Basic [base64 of username:password]
    if (authHeader == null || authHeader.startsWith("Basic ") == false) {
      throw new AuthenticationException("Missing or invalid Authorization header");
    }

    ourLog.info("authHeader: " + authHeader);
    final String base64 = authHeader.substring("Basic ".length());
    final String base64decoded = new String(Base64.decodeBase64(base64));
    final String[] parts = base64decoded.split(":");

    final String username = parts[0];
    final String password = parts[1];

    ourLog.info("username: '" + username + "'");
    ourLog.info("password: '" + password + "'");

    if (username == null || password == null) {
      ourLog.info("username or password is null -> aborting");
      throw new AuthenticationException("username or password missing");
    }

    if (!StringUtils.startsWith(username.toLowerCase(), USERNAME_PREFIX) || !password.equals(PASSWORD)) {
      ourLog.info("username or password dont match the criteria. username must begin with '" + USERNAME_PREFIX + "',  pw must be '" + PASSWORD + "'");
      throw new AuthenticationException("Invalid username or password");
    }

    // Return true to allow the request to proceed
    return true;
  }
}


//    // If the user is a specific patient, we create the following rule chain:
//    // Allow the user to read anything in their own patient compartment
//    // Allow the user to write anything in their own patient compartment
//    // If a client request doesn't pass either of the above, deny it
//    if (userIdPatientId != null) {
//      return new RuleBuilder()
//        .allow().read().allResources().inCompartment("Patient", userIdPatientId).andThen()
//        .allow().write().allResources().inCompartment("Patient", userIdPatientId).andThen()
//        .denyAll()
//        .build();
//    }
