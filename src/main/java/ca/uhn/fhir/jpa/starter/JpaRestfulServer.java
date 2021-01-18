package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.jpa.starter.interceptor.BasicSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletException;

@Import(AppProperties.class)
public class JpaRestfulServer extends BaseJpaRestfulServer {

  @Value("useauthentication")
  private boolean useAuthentication;
  @Autowired
  AppProperties appProperties;

  private static final long serialVersionUID = 1L;

  public JpaRestfulServer() {
    super();
  }

  @Override
  protected void initialize() throws ServletException {
    super.initialize();

    System.out.println("using authentication?   " + useAuthentication);

    if (useAuthentication) {
      System.out.println("using authentication");
      // Add your own customization here
      final BasicSecurityInterceptor authInterceptor = new BasicSecurityInterceptor();
      this.registerInterceptor(authInterceptor);

    }
  }
}
