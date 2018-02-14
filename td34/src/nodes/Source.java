package nodes;

import java.awt.Image;

import util.ImageBuffer;
import data.Message;

/**
 * The parent of all source nodes. Note that these nodes cannot receive message
 * -- indeed, the putInQueue() call will throw an UnsupportedOperationException.
 * 
 */
public abstract class Source extends Node {

    protected final Image image;
    public final int width, height;
    private final boolean loopAgain;
    protected int[] buffer;

    public final String channel;

    public Source(String fileName, String chan, boolean loop) {
        super("source " + fileName + " " + chan);
        this.image = ImageBuffer.getImage(fileName);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.channel = chan;
        this.loopAgain = loop;
    }

    /**
     * What data is sent by a source node depends on what kind of Source it is.
     */
    public abstract void sendData();

    // Node implementation

    @Override
    public final void putInQueue(Message line) {
        throw new UnsupportedOperationException("output only");
    }

    @Override
    public void run() {
        ImageBuffer imb = ImageBuffer.extract(this.image, getName());
        if (imb == null) return;
        this.buffer = imb.getPixels();
        do {
            this.sendData();
        } while (this.loopAgain);
    }

}
