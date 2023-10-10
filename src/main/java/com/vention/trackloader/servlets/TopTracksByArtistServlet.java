package com.vention.trackloader.servlets;

import com.vention.trackloader.services.TrackService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@WebServlet(urlPatterns = "/track/save-top-tracks-by-artist")
public class TopTracksByArtistServlet extends HttpServlet {
    private final TrackService trackService = new TrackService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String service = req.getHeader("service");
        if(!Objects.equals(service, "main")) throw new AccessDeniedException("Unavailable service" );
        String artist = req.getParameter("artist");
        String page = req.getParameter("page");
        resp.getWriter().print(trackService.getTopTracksByArtist(artist, page));
    }
}
