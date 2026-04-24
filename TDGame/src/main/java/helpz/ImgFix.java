/*
 * Decompiled with CFR 0.151.
 */
package helpz;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImgFix {
    public static BufferedImage getRotImg(BufferedImage img, int rotAngle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImg = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = newImg.createGraphics();
        g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
        g2d.drawImage((Image)img, 0, 0, null);
        g2d.dispose();
        return newImg;
    }

    public static BufferedImage buildImg(BufferedImage[] imgs) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();
        BufferedImage[] bufferedImageArray = imgs;
        int n = imgs.length;
        int n2 = 0;
        while (n2 < n) {
            BufferedImage img = bufferedImageArray[n2];
            g2d.drawImage((Image)img, 0, 0, null);
            ++n2;
        }
        g2d.dispose();
        return newImg;
    }

    public static BufferedImage getBuildRotImg(BufferedImage[] imgs, int rotAngle, int rotAtIndex) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();
        int i = 0;
        while (i < imgs.length) {
            if (rotAtIndex == i) {
                g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
            }
            g2d.drawImage((Image)imgs[i], 0, 0, null);
            if (rotAtIndex == i) {
                g2d.rotate(Math.toRadians(-rotAngle), w / 2, h / 2);
            }
            ++i;
        }
        g2d.dispose();
        return newImg;
    }

    public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs, BufferedImage secundImg, int rotAngle) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage[] arr = new BufferedImage[imgs.length];
        int i = 0;
        while (i < imgs.length) {
            BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
            Graphics2D g2d = newImg.createGraphics();
            g2d.drawImage((Image)imgs[i], 0, 0, null);
            g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
            g2d.drawImage((Image)secundImg, 0, 0, null);
            g2d.dispose();
            arr[i] = newImg;
            ++i;
        }
        return arr;
    }
}

