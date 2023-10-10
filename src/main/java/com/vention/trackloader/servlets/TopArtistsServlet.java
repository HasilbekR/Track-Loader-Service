package com.vention.trackloader.servlets;

import com.vention.trackloader.services.ArtistService;
import com.vention.trackloader.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@WebServlet(urlPatterns = "/artist/save-top-artists")
public class TopArtistsServlet extends HttpServlet {
    private final ArtistService artistService = new ArtistService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String service = req.getHeader("service");
        if(!Objects.equals(service, "main")) throw new AccessDeniedException("Unavailable service" );
        String page = req.getParameter("page");
        if(Utils.getRefresh()){
            resp.getWriter().print(artistService.getTopArtists());
        }else {
            resp.getWriter().print(artistService.saveTopArtists(page));
        }
    }
}