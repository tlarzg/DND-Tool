package com.rprescott.dndtool.server.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Utility to periodically count the number of active threads in the specified ThreadPoolExecutor.
 */
public class ThreadPoolPrinter {
    
    private Timer timer;
    
    public ThreadPoolPrinter(ThreadPoolExecutor threadPool, int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ActiveThreadCounterTask(threadPool), 0, seconds * 1000);
    }
    
    private class ActiveThreadCounterTask extends TimerTask {
        private ThreadPoolExecutor threadPool;
        
        public ActiveThreadCounterTask(ThreadPoolExecutor threadPool) {
            this.threadPool = threadPool;
        }
        
        @Override
        public void run() {
            System.out.println("Number of active clients: " + threadPool.getActiveCount());
        }
    }

}
