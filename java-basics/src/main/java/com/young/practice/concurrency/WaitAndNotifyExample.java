package com.young.practice.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaitAndNotifyExample {

    private static final Object lock = new Object();

    private static final List<Integer> tokenList = new ArrayList<>(10);

    public static class Producer implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(tokenList.size()==10){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        tokenList.add(new Random().nextInt(100));
                    }
                }
            }
        }
    }

    public static class Consumer implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    if(tokenList.size()>0){
                        System.out.println(tokenList.remove(tokenList.size()-1)+" tokenList size ["+tokenList.size()+"]");
                    }else {
                        lock.notifyAll();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }
}
