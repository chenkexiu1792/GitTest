package com.honghe.common.common.util.thread;

/**
 * @Auther: Administrator
 * @Date: 2019/6/22 0022 16:43
 * @Description:
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程池，所有方法均使用了拉姆达表达式
 * @Author sunchao
 * @Date: 2018-12-06 8:53
 * @Mender: Java通过Executors提供四种线程池，分别为：
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */

public class ThreadPool {

    private static ThreadPool threadPool;
    private ExecutorService executorService;

    public static ThreadPool getFixedInstance() {
        if (threadPool == null) {
            threadPool = new ThreadPool(600);
        }
        return threadPool;
    }


    public static ThreadPool getCacheInstance() {
        if (threadPool == null) {
            threadPool = new ThreadPool();
        }
        return threadPool;
    }

    ThreadPool(int threadCount) {
        fixedThreadPool(threadCount);
    }

    ThreadPool() {
        cachedThreadPool();
    }

    /**
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     */
    public void cachedThreadPool() {
        executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            try {
//                Thread.sleep(index * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            cachedThreadPool.execute(() -> System.out.println(index));
//        }
    }

    /**
     * 定长线程池。
     */
    public void fixedThreadPool(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            fixedThreadPool.execute(() -> {
//                try {
//                    System.out.println(index);
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }


    /**
     * 定时执行线程池
     */
    public void scheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(() -> System.out.println("delay 3 seconds"), 3, TimeUnit.SECONDS);
    }


    /**
     * 单线程线程池
     */
    public void singleThreadPool() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(() -> {
                try {
                    System.out.println(index);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

//    public static void main(String[] args) {
//        ThreadPool threadPool = new ThreadPool();
//        threadPool.cachedThreadPool();
//        threadPool.fixedThreadPool();
//        threadPool.singleThreadPool();
//        threadPool.cachedThreadPool();
//    }

}
