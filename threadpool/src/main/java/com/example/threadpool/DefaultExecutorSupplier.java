package com.example.threadpool;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultExecutorSupplier {
    /*
     * 决定线程数目的核心数目
     */
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    /*
     * 后台任务的线程池执行者
     */
    private final ThreadPoolExecutor mForBackgroundTasks;
    /*
     * 轻量级任务的线程执行者
     */
    private final ThreadPoolExecutor mForLightWeightBackgroundTasks;
    /*
     * 主线程任务的线程执行者
     */
    private final Executor mMainThreadExecutor;
    /*
     * 默认执行者的供应者的实例
     */
    private static DefaultExecutorSupplier sInstance;

    /*
     * 返回默认执行者的供应者的实例
     */
    public static DefaultExecutorSupplier getInstance() {
        if (sInstance == null) {
            synchronized (DefaultExecutorSupplier.class) {
                sInstance = new DefaultExecutorSupplier();
            }
        }
        return sInstance;
    }

        /*
         * 默认执行供应者的构造器
         */
    private DefaultExecutorSupplier() {

            // 设置优先级线程工厂
            ThreadFactory backgroundPriorityThreadFactory = new
                    PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

            // 给mForBackgroundTasks设置优先级线程池执行者
            mForBackgroundTasks = new PriorityThreadPoolExecutor(
                    NUMBER_OF_CORES * 2,
                    NUMBER_OF_CORES * 2,
                    60L,
                    TimeUnit.SECONDS,
                    backgroundPriorityThreadFactory
            );

            // 给mForLightWeightBackgroundTasks设置优先级线程池执行者
            mForLightWeightBackgroundTasks = new PriorityThreadPoolExecutor(
                    NUMBER_OF_CORES * 2,
                    NUMBER_OF_CORES * 2,
                    60L,
                    TimeUnit.SECONDS,
                    backgroundPriorityThreadFactory
            );

            // setting the thread pool executor for mMainThreadExecutor;
            // 给mMainThreadExecutor设置线程池执行者
            mMainThreadExecutor = new MainThreadExecutor();
        }

        /*
         * 为后台任务返回线程池的执行者
         */
        public ThreadPoolExecutor forBackgroundTasks () {
            return mForBackgroundTasks;
        }

        /*
         * 为轻量级后台任务返回线程池执行者
         */
        public ThreadPoolExecutor forLightWeightBackgroundTasks () {
            return mForLightWeightBackgroundTasks;
        }

        /*
         * 为主线程任务返回线程池执行者
         */
        public Executor forMainThreadTasks () {
            return mMainThreadExecutor;
        }
}