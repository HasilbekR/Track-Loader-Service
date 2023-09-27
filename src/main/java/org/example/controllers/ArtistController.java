package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.ArtistService;
import java.io.IOException;

@WebServlet(urlPatterns = "/artist/*")
public class ArtistController extends HttpServlet {
    private final ArtistService artistService = new ArtistService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getPathInfo();
        switch (requestUrl){
            case "/save-top-artists" -> {
                String page = req.getParameter("page");
                if(page == null) page = "1";
                resp.getWriter().print(artistService.saveTopArtists(Integer.valueOf(page)));
            }
        }
    }
}
