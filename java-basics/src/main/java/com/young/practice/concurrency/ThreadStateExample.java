package com.young.practice.concurrency;

/**
 * public static Thread.State toThreadState(int threadStatus) {
 * if ((threadStatus & JVMTI_THREAD_STATE_RUNNABLE) != 0) {
 * return RUNNABLE;
 * } else if ((threadStatus & JVMTI_THREAD_STATE_BLOCKED_ON_MONITOR_ENTER) != 0) {
 * return BLOCKED;
 * } else if ((threadStatus & JVMTI_THREAD_STATE_WAITING_INDEFINITELY) != 0) {
 * return WAITING;
 * } else if ((threadStatus & JVMTI_THREAD_STATE_WAITING_WITH_TIMEOUT) != 0) {
 * return TIMED_WAITING;
 * } else if ((threadStatus & JVMTI_THREAD_STATE_TERMINATED) != 0) {
 * return TERMINATED;
 * } else if ((threadStatus & JVMTI_THREAD_STATE_ALIVE) == 0) {
 * return NEW;
 * } else {
 * return RUNNABLE;
 * }
 * }
 * <p>
 * java线程一共有6种状态,
 * 新建(new)：创建完成线程后状态就为new
 * 运行(runnable)：调用了start方法后，如果没有cpu资源那么状态为ready，分配了cpu资源那么状态为running
 * 阻塞(blocked)：线程被锁阻塞
 * 等待(waiting)：线程进入等待状态，等待其他线程做出动作（唤醒、中断）
 * 超时等待(timed_waiting)：线程进入等待状态，不过超时后会返回
 * 终止（terminated):线程执行完毕
 */
public class ThreadStateExample {
    public static void main(String[] args) throws InterruptedException {

        final Object lock = new Object();
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                    lock.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    //wait后，当前线程会释放锁
                    lock.wait();
                    System.out.println("thread1 state " + Thread.currentThread().getState().name());
                    Thread.sleep(1500);
                    lock.wait(500);
                    System.out.println("thread1 state " + Thread.currentThread().getState().name());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //new状态
        System.out.println("thread1 state " + thread1.getState().name());
        thread1.start();
        //runnable状态，
        System.out.println("thread1 state " + thread1.getState().name());
        //由于thread1调用了wait方法，会释放锁，所以thread2会获取到锁并开始运行
        thread2.start();
        //由于thread2先sleep了100毫秒，所以这时候thread1的状态是WATING
        System.out.println("thread1 state " + thread1.getState().name());
        //这里等待1秒，让微观世界更宏观
        Thread.sleep(1000);
        //等待2秒以后，thread2执行了notifyall，所以thread1又会被唤醒，这时候thread1的状态又变成了runnable
        System.out.println("thread1 state " + thread1.getState().name());
        Thread.sleep(600);
        //thread1执行了wait(500)所以，thread1这时候又TIMED_WAITING了
        System.out.println("thread1 state " + thread1.getState().name());
        //thread1 wait超时后，可以继续运行，这时候状态为runnable
        Thread.sleep(1000);
        System.out.println("thread1 state " + thread1.getState().name());
        //最后thread1执行完以后状态变为terminated
        Thread.sleep(1000);
        System.out.println("thread1 state " + thread1.getState().name());
    }
}
