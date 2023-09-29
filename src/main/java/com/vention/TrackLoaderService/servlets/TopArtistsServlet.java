package com.vention.TrackLoaderService.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vention.TrackLoaderService.service.ArtistService;

import java.io.IOException;

@WebServlet(urlPatterns = "/artist/save-top-artists")
public class TopArtistsServlet extends HttpServlet {
    private final ArtistService artistService = new ArtistService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = req.getParameter("page");
        if (page == null) page = "1";
        resp.getWriter().print(artistService.saveTopArtists(Integer.valueOf(page)));
    }
}
