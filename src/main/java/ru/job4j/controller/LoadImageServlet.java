package ru.job4j.controller;

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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        File dirForImages = (File) getServletContext().getAttribute("dirForImages");
        try (final OutputStream outStream = new BufferedOutputStream(resp.getOutputStream())) {
            Files.copy(new File(dirForImages, name).toPath(), outStream);
        }
    }
}
