package com.vention.trackloader.servlets;

import com.vention.trackloader.services.TrackService;
import com.vention.trackloader.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@WebServlet(urlPatterns = "/track/save-top-tracks")
public class TopTracksServlet extends HttpServlet {
    private final TrackService trackService = new TrackService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String service = req.getHeader("service");
        if(!Objects.equals(service, "main")) throw new AccessDeniedException("Unavailable service" );
        String page = req.getParameter("page");
        if(Utils.getRefresh()){
            resp.getWriter().print(trackService.getTopTracks());
        }else {
            resp.getWriter().print(trackService.saveTopTracks(page));
        }
    }
}