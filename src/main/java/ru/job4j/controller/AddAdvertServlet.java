package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.model.Advert;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class AddAdvertServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 10;
    private static File dirForImages;

    @Override
    public void init() throws ServletException {
        dirForImages = (File) getServletContext().getAttribute("dirForImages");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/addAdvert.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ServletContext servletContext = getServletContext();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setRepository((File) servletContext.getAttribute("javax.servlet.context.tempdir"));
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        fileUpload.setFileSizeMax(FILE_MAX_SIZE);

        List<FileItem> items = List.of();
        try {
            items = fileUpload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Advert advert = null;
        File file = null;
        for (FileItem item : items) {
            if (!item.isFormField()) {
                file = createImageFile(dirForImages, item);
                continue;
            }
            String advertJson = item.getString();
            ObjectMapper mapper = new ObjectMapper();
            advert = mapper.readValue(advertJson, Advert.class);
        }
        if (Objects.nonNull(file) && file.exists()) {
            advert.setPhotoName(file.getName());
        }
        if (!SERVICE.addAdvert(advert)) {
            file.delete();
        }
    }

    private File createImageFile(File dirForImages, FileItem fileItem) {
        String imgFullFileName = fileItem.getName();
        File imgFile = new File(dirForImages, imgFullFileName);
        while (imgFile.exists()) {
            int i = 0;
            if (imgFullFileName.contains(".")) {
                imgFullFileName = imgFullFileName.replace(".", ++i + ".");
            }
            imgFile = new File(dirForImages, imgFullFileName);
        }
        try {
            fileItem.write(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgFile;
    }
}
