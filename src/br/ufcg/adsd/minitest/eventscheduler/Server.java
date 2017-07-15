package br.ufcg.adsd.minitest.eventscheduler;

import java.util.Random;

public class Server {

    private Buffer queue1 = new Buffer();
    private Buffer queue2 = new Buffer();

    private Random random = new Random();

    public boolean isFree() {
        return queue1.isEmpty() && queue2.isEmpty();
    }

    public Client next() {
        return random.nextDouble() < .3 ? queue2.free() : queue1.free();
    }

    public void receives(Client client) {
        if (client.getClazz() == 1)
            queue1.add(client);
        else
            queue2.add(client);
    }

    public int[] state() {
        return new int[] {queue1.size(), queue2.size()};
    }
}
