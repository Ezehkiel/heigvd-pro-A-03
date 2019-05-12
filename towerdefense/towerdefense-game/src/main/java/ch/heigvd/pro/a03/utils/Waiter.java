package ch.heigvd.pro.a03.utils;

public class Waiter<Data> {

    private Data data = null;

    private boolean isReady() {
        return data != null;
    }

    public void waitData() {
        while (!isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Waiter: Failed sleep.");
            }
        }
    }

    public void send(Data data) {
        this.data = data;
    }

    public Data receive() {
        Data tmp = data;
        data = null;
        return tmp;
    }
}
