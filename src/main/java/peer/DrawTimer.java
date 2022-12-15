package peer;

public class DrawTimer extends Thread {
    private boolean isStarted = false;

    public boolean isStarted() {
        return isStarted;
    }

    public void startTimer(){
        isStarted = true;
    }

    public void run(){
        while (!Thread.interrupted()){
            if (isStarted) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isStarted = false;
            }
       }
    }
    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
