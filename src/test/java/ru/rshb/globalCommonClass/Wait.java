package ru.rshb.globalCommonClass;

public class Wait {
    public static void waitpage(double waittimeInsecond) {
        try {
            Thread.sleep((long) (waittimeInsecond * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}