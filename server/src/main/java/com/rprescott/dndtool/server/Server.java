package com.rprescott.dndtool.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.rprescott.dndtool.server.utils.ThreadPoolPrinter;


public class Server {
    
    private static final int NUM_THREADS_IN_POOL = 2;
    private ServerSocket serverSocket;
    private List<Socket> clients = new ArrayList<Socket>();
    
    /** Thread pool of fixed size. */
    // TODO: Grab value from config file / database table.
    private ThreadPoolExecutor threadPool;

    
    public void run() throws IOException {
        threadPool = new ThreadPoolExecutor(NUM_THREADS_IN_POOL, NUM_THREADS_IN_POOL, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        // TODO: Do I want to have the server send a heartbeat out to all the clients or have the clients ask the server?
        //new ClientHeartbeatThread(this);
        new ThreadPoolPrinter(threadPool, 5);
        // Creating a new ServerSocket. The argument is an integer which is the port number to accept
        // requests on.
        // TODO: Grab port value from config file / database table.
        serverSocket = new ServerSocket(1337);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            System.out.println("Connection established with client.");
            threadPool.execute(new ClientWorkerThread(clientSocket));
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (serverSocket != null) {
            serverSocket.close();
        }
        super.finalize();
    }
    
    public List<Socket> getConnectedClients() {
        return clients;
    }
}