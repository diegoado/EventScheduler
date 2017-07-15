package br.ufcg.adsd.minitest.eventscheduler;

import java.util.LinkedList;
import java.util.Queue;

class Buffer {

    private Queue<Client> queue = new LinkedList<>();

    synchronized int size() {
        return queue.size();
    }

    synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    synchronized void add(Client client) {
        queue.add(client);
    }

    synchronized Client free() {
        return isEmpty() ? null : queue.remove();
    }
}
