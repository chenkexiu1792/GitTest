package com.honghe.common.common.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String mPrefix;

    private final boolean mDaemo;

    private final ThreadGroup mGroup;

    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        mPrefix = prefix + "-thread-";
        mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return newThread(runnable, false);
    }

    public Thread newThread(Runnable runnable, boolean mDaemo) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }

    public static void main(String[] args) {
        ExecutorService executorService = null;
        NamedThreadFactory factory = new NamedThreadFactory("t");

        try {
            executorService = Executors.newFixedThreadPool(3, factory);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    System.out.println("thread group name:" + Thread.currentThread().getThreadGroup().getName());
                }
            });

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    System.out.println("thread group name:" + factory.getThreadGroup().getName());
                }
            });

            factory.newThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    System.out.println("thread group name:" + factory.getThreadGroup().getName());
                }
            }).start();

            factory.newThread(() -> {
                System.out.println("thread name: " + Thread.currentThread().getName());
                System.out.println("thread group name:" + factory.getThreadGroup().getName());
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
