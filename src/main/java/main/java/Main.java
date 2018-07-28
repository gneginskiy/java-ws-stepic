package main.java;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);//todo
        handler.addServlet(new ServletHolder(new MirrorServlet()), "/mirror/*");
       // handler.addServlet(new ServletHolder(new MirrorServlet()), "/*");
        server.setHandler(handler);
        server.start();
        server.join();
         final Logger LOG = Log.getLogger(Server.class);
        LOG.info("Server started",Main.class);
        System.out.println("Server started");
        System.err.println("Server started");
    }

    private static class MirrorServlet extends HttpServlet {
        public MirrorServlet() {

        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            /*
            При получении GET запроса с параметром key=value сервлет должен вернуть в response строку содержащую value.
            Например, при GET запросе /mirror?key=hello сервер должен вернуть страницу, на которой есть слово "hello".
             */
            //super.doGet(req, resp);
            Map<String, String[]> parameterMap = req.getParameterMap();
            parameterMap.forEach((k, v) -> {
                try {
                    resp.getWriter().println(Arrays.stream(v).collect(Collectors.joining()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
        }
    }
}
