package com.rprescott.dndtool.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.rprescott.dndtool.server.utils.ThreadPoolPrinter;

@Component
public class Server implements DisposableBean {
    
    // TODO: Grab value from config file / database table.
    private static final int NUM_THREADS_IN_POOL = 5;
    private ServerSocket serverSocket;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    
    /** Thread pool of fixed size. */
    private ThreadPoolExecutor threadPool;
    
    public void run() throws IOException {
        threadPool = new ThreadPoolExecutor(NUM_THREADS_IN_POOL, NUM_THREADS_IN_POOL, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        new ThreadPoolPrinter(threadPool, 5);
        // Creating a new ServerSocket. The argument is an integer which is the port number to accept
        // requests on.
        // TODO: Grab port value from config file / database table.
        serverSocket = new ServerSocket(1337);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with client.");
            // Send the work onto a separate worker thread to allow other clients to connect.
            ClientWorkerThread worker = new ClientWorkerThread(clientSocket);
            beanFactory.autowireBean(worker);
            threadPool.execute(worker);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}