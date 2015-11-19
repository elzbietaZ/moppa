package org.moppa.MoppaAPI;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.moppa.MoppaAPI.MobileAPI.MobileHandler;

public class MobileHandlerTest extends JerseyTest {
	@Override
    protected Application configure() {
        return new ResourceConfig(MobileHandler.class);
    }
 
    @Test
    public void test() {
        final Response response = target("v1/tasks/test").request().get();
        System.out.println(response);
        assertEquals(response.getStatus(), 200);
        assertEquals(response.readEntity(String.class), "ok");
    }
}
