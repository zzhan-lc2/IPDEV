package com.ipdev.cnipr.dao.patent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.ipdev.common.IpdException;
import com.ipdev.common.dao.patent.ImageHandler;
import com.ipdev.common.net.HttpMethodsInterface;

public class ImageCniprHandler implements ImageHandler {

    HttpMethodsInterface httpUtil = new HttpMethodsCNIPR(true);
    AuthTokenManager tokenManager;

    String picUrl = "http://pic.cnipr.com:8080/";

    public ImageCniprHandler() {
    }

    public void setHttpUtil(HttpMethodsInterface httpUtil) {
        this.httpUtil = httpUtil;
    }

    public void setTokenManager(AuthTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    static final String DRAWS_FORMAT = ".gif";
    @Override
    public BufferedImage downloadDraws(String drawsPath) {
        Preconditions.checkNotNull(drawsPath, "drawsPath cannot be null");

        int index = StringUtils.lastIndexOf(drawsPath, "/");
        if (index == -1) {
            throw new IllegalArgumentException("Missing required / in drawsPath");
        }

        String picName = StringUtils.substring(drawsPath, index + 1);
        // some pic name has . in it (such as 201410026743.6), we need to get rid of .6
        index = StringUtils.indexOf(picName, ".");
        if (index > 0) {
            picName = StringUtils.substring(picName, 0, index);
        }

        StringBuilder sb = new StringBuilder()
            .append(picUrl).append(drawsPath)
            .append("/").append(picName).append(DRAWS_FORMAT);

        String url = sb.toString();
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            return image;
        } catch (MalformedURLException e) {
            throw new IpdException("Caught MalformedURLException for URL:" + url, e);
        } catch (IOException e) {
            throw new IpdException("Caught IOException for URL:" + url, e);
        }

    }

    @Override
    public Map<String, BufferedImage> downloadTiffs(String tiffDistributePath, int pages) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, BufferedImage> downloadThumenailGifs(String tiffDistributePath, int pages) {
        // TODO Auto-generated method stub
        return null;
    }

}
