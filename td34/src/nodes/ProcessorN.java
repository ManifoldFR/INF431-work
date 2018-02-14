package nodes;

import data.MessageProcessor;
import data.MessageQueue;

public class ProcessorN extends Processor {

    public ProcessorN(int numThreads, MessageQueue q, MessageProcessor p,
            String name) {
        super(q, p, name);
    }

    @Override
    public void init() {
        // your code here
    }
}
