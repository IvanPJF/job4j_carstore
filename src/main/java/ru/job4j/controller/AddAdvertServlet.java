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

    private final Service service = ValidateService.getInstance();
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 10;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/addAdvert.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FileItem> items = getListFileItems(req);
        Advert advert = null;
        File imageFile = null;
        for (FileItem item : items) {
            if (!item.isFormField()) {
                imageFile = createImageFile(item);
                continue;
            }
            String advertJson = item.getString();
            ObjectMapper mapper = new ObjectMapper();
            advert = mapper.readValue(advertJson, Advert.class);
            if (Objects.nonNull(imageFile) && imageFile.exists()) {
                advert.setPhotoName(imageFile.getName());
            }
        }
        if (!service.addAdvert(advert)) {
            resp.sendError(500, "Failed to add file");
        }
    }

    private List<FileItem> getListFileItems(HttpServletRequest request) {
        ServletContext servletContext = getServletContext();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        fileItemFactory.setRepository(repository);
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        fileUpload.setFileSizeMax(FILE_MAX_SIZE);
        try {
            return fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private File createImageFile(FileItem fileItem) {
        File dirForImages = (File) getServletContext().getAttribute("dirForImages");
        String imgFileName = fileItem.getName();
        File imgFile = new File(dirForImages, imgFileName);
        while (imgFile.exists()) {
            int i = 0;
            if (imgFileName.contains(".")) {
                imgFileName = imgFileName.replace(".", ++i + ".");
            } else {
                imgFileName += ++i;
            }
            imgFile = new File(dirForImages, imgFileName);
        }
        try {
            fileItem.write(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgFile;
    }
}
