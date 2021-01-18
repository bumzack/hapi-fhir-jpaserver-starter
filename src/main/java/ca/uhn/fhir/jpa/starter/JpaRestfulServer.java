package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.jpa.starter.interceptor.BasicSecurityInterceptor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletException;

@Import(AppProperties.class)
public class JpaRestfulServer extends BaseJpaRestfulServer {

  @Autowired
  AppProperties appProperties;

  private static final long serialVersionUID = 1L;

  public JpaRestfulServer() {
    super();
  }

  @Override
  protected void initialize() throws ServletException {
    super.initialize();

    System.out.println("using authentication?   " + BooleanUtils.isTrue(appProperties.getUse_authentication()));

    if (BooleanUtils.isTrue(appProperties.getUse_authentication())) {
      System.out.println("using authentication");
      // Add your own customization here
      final BasicSecurityInterceptor authInterceptor = new BasicSecurityInterceptor();
      this.registerInterceptor(authInterceptor);

    }
  }
}
