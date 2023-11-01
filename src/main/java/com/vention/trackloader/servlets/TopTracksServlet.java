package com.vention.trackloader.servlets;

import com.vention.trackloader.exceptions.BadRequestException;
import com.vention.trackloader.services.TrackService;
import com.vention.trackloader.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/track/save-top-tracks")
public class TopTracksServlet extends HttpServlet {
    private final TrackService trackService = new TrackService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String page = req.getParameter("page");
            if (Utils.getRefresh()) {
                resp.getWriter().print(trackService.saveTopTracks(page));
            } else {
                resp.getWriter().print(trackService.getTopTracks());
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}