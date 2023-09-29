package com.vention.TrackLoaderService.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.vention.TrackLoaderService.service.TrackService;

import java.io.IOException;

@WebServlet(urlPatterns = "/track/save-top-tracks")
public class TopTracksServlet extends HttpServlet {
    private final TrackService trackService = new TrackService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = req.getParameter("page");
        if (page == null) page = "1";
        resp.getWriter().print(trackService.saveTopTracks(Integer.valueOf(page)));
    }
}
