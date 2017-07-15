import br.ufcg.adsd.minitest.eventscheduler.Client;
import br.ufcg.adsd.minitest.eventscheduler.Server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Scheduler {

    private Server server = new Server();
    private Random random = new Random();

    private Thread inputOne;
    private Thread inputTwo;
    private Thread output;

    Scheduler() {
        inputOne = new Thread(new ComingClientClassOne());
        inputTwo = new Thread(new ComingClientClassTwo());

        output = new Thread(new EndService());
    }

    void start() {
        inputOne.start(); inputTwo.start(); output.start();
    }

    void join() throws InterruptedException {
        inputOne.join(); inputTwo.join(); output.join();
    }

    private synchronized void print(String event, int[] serverState, int clientIdentifier) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println("Event Type: " + event +
                ", Event Moment: " + dateFormat.format(new Date()));
        System.out.println("Clients in Queue One: "
                + String.valueOf(serverState[0]));
        System.out.println("Clients in Queue Two: "
                + String.valueOf(serverState[1]));
        System.out.println("Client serviced: "
                + String.valueOf(clientIdentifier));
        System.out.println();
    }

    private class ComingClientClassOne implements Runnable {
        int wait;

        @Override
        public void run() {
            while (true) {
                Client client = new Client(1, random.nextInt(101));

                server.receives(client);
                print("Input", server.state(), client.identifier());

                wait = (1 + random.nextInt(10)) * 3000;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ComingClientClassTwo implements Runnable {
        int wait;

        @Override
        public void run() {
            while (true) {
                Client client = new Client(2, random.nextInt(101));

                server.receives(client);
                print("Input", server.state(), client.identifier());

                wait = (1 + random.nextInt(5)) * 3000;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class EndService implements Runnable {
        int wait;

        @Override
        public void run() {
            while (true) {
                Client client = server.next();

                if (client != null) {
                    print("Output", server.state(), client.identifier());
                    wait = (3 + random.nextInt(7)) * 1000;
                } else {
                    wait = 500;
                }

                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
