package ru.netology.graphics;

import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private Schema schema = new Schema();


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();
        double ratio = (double) width / height;

        if (maxRatio != 0 && maxRatio < ratio) throw new BadImageSizeException(ratio, maxRatio); //ratio check
        if ((maxWidth != 0 && maxWidth < width) || (maxHeight != 0 && maxHeight < height)) {
            double heightRatio = (double) maxHeight / height;
            double widthRatio = (double) maxWidth / width;
            double newRatio = (heightRatio == 0 || widthRatio == 0) ? Math.max(heightRatio, widthRatio)
                    : Math.min(heightRatio, widthRatio);
            width *= newRatio;
            height *= newRatio;
        }
        ImageIO.write(img, "png", new File("out.png"));
        Image scaledImage = img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        ImageIO.write(bwImg, "png", new File("out1.png"));
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        ImageIO.write(bwImg, "png", new File("out2.png"));
        char[][] chars = getCharsByPixelLevel(width, height, bwRaster);
        return getStringImg(chars);
    }

    private char[][] getCharsByPixelLevel(int width, int height, WritableRaster bwRaster) {
        char[][] chars = new char[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                chars[w][h] = schema.convert(color);
            }
        }
        return chars;
    }

    private String getStringImg(char[][] chars) {
        StringBuilder str = new StringBuilder();
        for (int j = 0; j < chars[0].length; j++) {
            for (int i = 0; i < chars.length; i++) {
                str.append(chars[i][j]).append(chars[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = (Schema) schema;
    }

}
