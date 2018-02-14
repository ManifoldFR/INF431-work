package nodes;

import data.MessageProcessor;
import data.MessageQueue;

public class ProcessorPool extends Processor {

    public ProcessorPool(int concur, MessageQueue q, MessageProcessor p,
            String name) {
        super(q, p, name);
    }

    @Override
    public void run() {
        // your code here
    }

}
