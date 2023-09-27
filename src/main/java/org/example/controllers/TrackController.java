package org.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TrackService;

import java.io.IOException;

@WebServlet(urlPatterns = "/track/*")
public class TrackController extends HttpServlet {
    private final TrackService trackService = new TrackService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getPathInfo();
        switch (requestUrl){
            case "/save-top-tracks" -> {
                String page = req.getParameter("page");
                if(page == null) page = "1";
                resp.getWriter().print(trackService.saveTopTracks(Integer.valueOf(page)));
            }
            case "/save-top-tracks-by-artist" -> {
                String artist = req.getParameter("artist");
                String page = req.getParameter("page");
                if(page == null) page = "1";
                resp.getWriter().print(trackService.saveTopTracksByArtist(artist, Integer.valueOf(page)));

            }
        }
    }
}
