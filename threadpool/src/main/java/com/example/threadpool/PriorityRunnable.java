package com.example.threadpool;
/**
 * 创建人: Administrator
 * 创建时间: 2019/01/26
 * 描述: 创建一个优先级线程
 **/

public class PriorityRunnable implements Runnable {
    private final Priority priority;

    public PriorityRunnable(Priority priority) {
        this.priority = priority;
    }
    @Override
    public void run() {
        //这里什么都不要做
    }

    public Priority getPriority() {
        return priority;
    }
}
