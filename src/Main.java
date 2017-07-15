public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scheduler scheduler = new Scheduler();

        scheduler.start();
        scheduler.join();
    }


}
