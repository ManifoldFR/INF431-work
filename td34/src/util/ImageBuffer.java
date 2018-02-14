package util;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ImageBuffer {
    final int width, height;
    final String name;
    private int[] pixels;
    private ColorModel cModel;
    private MemoryImageSource source;

    // constructeur depuis une image source
    public ImageBuffer(int w, int h, int[] p, ColorModel cm, String s) {
        width = w;
        height = h;
        pixels = p;
        cModel = cm;
        name = s;
    }

    public ImageBuffer(int w, int h, int[] p, String s) {
        width = w;
        height = h;
        pixels = new int[p.length];
        cModel = ColorModel.getRGBdefault();
        name = s;
        setPixels(p);
    }

    public ImageBuffer(int w, int h, int[] r, int[] g, int[] b, String s) {
        width = w;
        height = h;
        pixels = new int[w * h];
        cModel = ColorModel.getRGBdefault();
        name = s;
        setPixels(r, g, b);
    }

    public void setPixels(int[] p) {
        for (int i = 0; i < pixels.length; ++i)
            pixels[i] = 0xFF000000 | (p[i] & 0xFFFFFF);
        if (source != null) source.newPixels(pixels, cModel, 0, width);
    }

    public void setPixels(int[] r, int[] g, int[] b) {
        for (int i = 0; i < pixels.length; ++i)
            pixels[i] = 0xFF000000 /* opaque */| ((r[i] & 0xFF) << 16)
                    | ((g[i] & 0xFF) << 8) | (b[i] & 0xFF);
        if (source != null) source.newPixels(pixels, cModel, 0, width);
    }

    public static ImageBuffer load(String filename) {
        Image img = getImage(filename);
        return extract(img, filename);
    }

    private static final FileSystem fs = FileSystems.getDefault();
    private static final Path imgs = fs.getPath("imgs");

    public static Image getImage(String filename) {
        Path path = fs.getPath(filename);
        if (!path.isAbsolute())
            path = imgs.resolve(path);
        Image img = Toolkit.getDefaultToolkit().getImage(path.toString());
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(img, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException e) {
            return null;
        }
        return img;
    }

    // conversion d'une Image en ImageBuffer
    public static ImageBuffer extract(Image img, String name) {
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        if (width <= 0 || height <= 0) {
            System.err.println("Bad input image file for " + name);
            return null;
        }
        int[] pixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0,
                                           width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            return null;
        }
        ColorModel cm = pg.getColorModel();
        return new ImageBuffer(width, height, pixels, cm, name);
    }

    // reconstruit une Image avec ce ImageBuffer (pour la dessiner par ex.)
    public Image createImage() {
        if (source == null) {
            source = new MemoryImageSource(width, height, cModel, pixels, 0,
                                           width);
            source.setAnimated(true);
        }
        Image img = Toolkit.getDefaultToolkit().createImage(source);
        return img;
    }

    // retournent un tableau avec la composante rouge, verte ou bleue

    public int[] getRed() {
        int[] buf = new int[pixels.length];
        for (int i = 0; i < buf.length; ++i)
            buf[i] = (int) cModel.getRed(pixels[i]);
        return buf;
    }

    public int[] getGreen() {
        int[] buf = new int[pixels.length];
        for (int i = 0; i < buf.length; ++i)
            buf[i] = (int) cModel.getGreen(pixels[i]);
        return buf;
    }

    public int[] getBlue() {
        int[] buf = new int[pixels.length];
        for (int i = 0; i < buf.length; ++i)
            buf[i] = (int) cModel.getBlue(pixels[i]);
        return buf;
    }

    public int[] getPixels() {
        return this.pixels;
    }
}
