package com.vention.trackloader.servlets;

import com.vention.trackloader.exceptions.BadRequestException;
import com.vention.trackloader.services.ArtistService;
import com.vention.trackloader.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/artist/save-top-artists")
public class TopArtistsServlet extends HttpServlet {
    private final ArtistService artistService = new ArtistService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String page = req.getParameter("page");
        try {
            if (Utils.getRefresh()) {
                resp.getWriter().print(artistService.saveTopArtists(page));
            } else {
                resp.getWriter().print(artistService.getTopArtists());
            }
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}