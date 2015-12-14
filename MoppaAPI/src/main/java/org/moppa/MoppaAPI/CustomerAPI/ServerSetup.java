package org.moppa.MoppaAPI.CustomerAPI;

import org.moppa.MoppaAPI.MobileAPI.*;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

public class ServerSetup {

  private static final URI BASE_URI = URI.create("http://localhost:8080/MoppaCustomerAPI");
  private static HttpServer server;

  public static void runTheServer() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0");
    beanConfig.setScan(true);
    beanConfig.setResourcePackage(Authentication.class.getPackage().getName());
    beanConfig.setBasePath(BASE_URI.toString());
    beanConfig.setDescription("MoppaAPI");
    beanConfig.setTitle("Moppa API");
    server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp());
    stopTheServer();
  }

  public static void stopTheServer() {
    System.out.println(String.format("Application started.%nHit enter to stop it..."));
    try {
      System.in.read();
    } catch (IOException ex) {
      Logger.getLogger(ServerSetup.class.getName()).log(Level.SEVERE, null, ex);
    }
    server.shutdownNow();
    System.exit(0);
  }

  /**
   * Server configuration.
   */
  public static void main(String[] args) {
    runTheServer();
  }

  /** Creating the application. */
  public static ResourceConfig createApp() {
    return new ResourceConfig()
        .packages(Authentication.class.getPackage().getName(), "com.wordnik.swagger.jaxrs.listing")
        .packages(TasksHandler.class.getPackage().getName(), "com.wordnik.swagger.jaxrs.listing")
        .packages(MobileHandler.class.getPackage().getName(), "com.wordnik.swagger.jaxrs.listing")
        .register(createMoxyJsonResolver());
  }

  /** Creating a json resolver. */
  public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
    final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
    Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
    namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
    moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
    return moxyJsonConfig.resolver();
  }

}
