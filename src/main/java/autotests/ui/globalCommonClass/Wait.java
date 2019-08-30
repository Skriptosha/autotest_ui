package autotests.ui.globalCommonClass;

public class Wait {
    public static void waitPage(double waitTimeInSeconds) {
        try {
            Thread.sleep((long) (waitTimeInSeconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}