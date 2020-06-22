package ru.job4j.controller;

import javax.servlet.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImagesDirFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        if (Objects.isNull(servletContext.getAttribute("dirForImages"))) {
            String imagesDirPath = servletContext
                    .getRealPath(servletContext.getInitParameter("name.dir.images"));
            File dirForImages = new File(imagesDirPath);
            if (dirForImages.exists() || dirForImages.mkdir()) {
                servletContext.setAttribute("dirForImages", dirForImages);
                return;
            }
            throw new RuntimeException("Image catalog not created!!!");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) {
    }

}
