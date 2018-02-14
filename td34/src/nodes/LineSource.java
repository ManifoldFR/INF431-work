package nodes;

import data.LineMessage;

public class LineSource extends Source {

    public LineSource(String file, String channel, boolean loop) {
        super(file, channel, loop);
    }

    @Override
    public void sendData() {
        int[] line = new int[width];
        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j, ++k)
                line[j] = buffer[k];
            // System.out.format("LineSource: sent %s.%d\n", this.channel, i);
            this.forward(new LineMessage(this.channel, i, line));
        }
    }

}
