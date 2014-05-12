package com.ipdev.common.dao.patent;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface ImageHandler {

    BufferedImage downloadDraws(String drawsPath);

    Map<String, BufferedImage> downloadTiffs(String tiffDistributePath, int pages);

    Map<String, BufferedImage> downloadThumenailGifs(String tiffDistributePath, int pages);
}
