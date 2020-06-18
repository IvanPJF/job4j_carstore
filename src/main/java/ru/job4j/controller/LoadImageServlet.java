package ru.job4j.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class LoadImageServlet extends HttpServlet {

    private static File dirForImages;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        String imagesDirPath = servletContext.getRealPath("images");
        dirForImages = new File(imagesDirPath);
        if (dirForImages.exists() || dirForImages.mkdir()) {
            servletContext.setAttribute("dirForImages", dirForImages);
            return;
        }
        throw new RuntimeException("Image catalog not created!!!");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        try (final OutputStream outStream = new BufferedOutputStream(resp.getOutputStream())) {
            Files.copy(new File(dirForImages, name).toPath(), outStream);
        }
    }
}
