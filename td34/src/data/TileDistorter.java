package data;

public class TileDistorter implements MessageProcessor {
    private final PixelDistorter distorter;
    private final int threshold;

    public TileDistorter(int parallelism, PixelDistorter distorter,
            int threshold) {
        this.distorter = distorter;
        this.threshold = threshold;
    }

    public TileDistorter(int parallelism, PixelDistorter distorter) {
        this(parallelism, distorter, 64);
    }

    private Message processTile(TileMessage msg) {
        // replace this with your code
        return msg;
    }

    @Override
    public Message process(Message msg) {
        if (msg instanceof TileMessage)
            return this.processTile((TileMessage) msg);
        else {
            throw new IllegalArgumentException("TileDistorter: invalid message");
        }
    }

}
