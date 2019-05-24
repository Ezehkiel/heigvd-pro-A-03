package ch.heigvd.pro.a03.utils;

/**
 * Waits actively for a daa.
 * @param <Data> data type
 */
public class Waiter<Data> {

    private Data data = null;

    /**
     * Checks if the data arrived.
     * @return true if the data arrived
     */
    private boolean isReady() {
        return data != null;
    }

    /**
     * Waits actively for the data to arrive.
     */
    public void waitData() {
        while (!isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Waiter: Failed sleep.");
            }
        }
    }

    /**
     * Send data to the waiter.
     * @param data data to send
     */
    public void send(Data data) {
        this.data = data;
    }

    /**
     * Reads the data.
     * @return data read.
     */
    public Data receive() {
        Data tmp = data;
        data = null;
        return tmp;
    }
}
