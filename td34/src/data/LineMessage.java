package data;

public class LineMessage extends Message {
    private static final long serialVersionUID = 5560420984375724183L;

    public final int lineNum;
    public final int[] content;

    public LineMessage(String channel, int lineNum, int[] content) {
        super(channel);
        this.lineNum = lineNum;
        this.content = content.clone();
    }

    public void getContent(int[] buffer, int offset) {
        int dst = offset;
        for (int i = 0; i < this.content.length && dst < buffer.length; i++)
            buffer[dst++] = this.content[i];
    }

    public int[] getContent(int length) {
        int[] buffer = new int[length];
        getContent(buffer, 0);
        return buffer;
    }
}
