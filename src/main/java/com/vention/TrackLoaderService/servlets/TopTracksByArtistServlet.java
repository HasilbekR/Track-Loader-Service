package com.vention.TrackLoaderService.servlets;

import com.vention.TrackLoaderService.service.TrackService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/track/save-top-tracks-by-artist")
public class TopTracksByArtistServlet extends HttpServlet {
    private final TrackService trackService = new TrackService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String artist = req.getParameter("artist");
        String page = req.getParameter("page");
        if (page == null) page = "1";
        resp.getWriter().print(trackService.saveTopTracksByArtist(artist, Integer.valueOf(page)));
    }
}

