package doc.mngmnt.jetty.config;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;

import java.util.Objects;

public class JettyDocumentManagementApplication {
    private static final int PORT = 9999;

    public static void main(String[] args) throws Exception {
        final String webapp = JettyDocumentManagementApplication.class.getClassLoader().getResource("webapp").toExternalForm();
        Server server = initServer(webapp);
        server.start();
        server.join();
    }

    private static Server initServer(final String webXmlDirectory) {
        Server server = new Server(PORT);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase(webXmlDirectory + "/web.xml");
        webAppContext.setConfigurationDiscovered(true);
        Configuration.ClassList.setServerDefault(server);
        webAppContext.setConfigurations(new Configuration[]{
            new AnnotationConfiguration(),
            new WebXmlConfiguration(),
            new PlusConfiguration(),
            new MetaInfConfiguration(),
            new FragmentConfiguration(),
            new EnvConfiguration()
        });
        webAppContext.setParentLoaderPriority(true);

        server.setHandler(webAppContext);

        return server;
    }
}
