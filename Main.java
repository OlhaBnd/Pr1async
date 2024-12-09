import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static List<Thread> allDayClients = new ArrayList<>();
    public static final Semaphore waiterPlaces = new Semaphore(2);
    public static final Semaphore orderPlaces = new Semaphore(0);
    public static volatile boolean isOpen = false;

    public static void main(String[] args) throws InterruptedException {
        Runnable cafe = () -> {
            Thread timeThread = new Thread(new TimeRunnable(), "timeThread");
            Thread baristaThread = new Thread(new Barista(), "baristaThread");

            timeThread.start();
            while (TimeRunnable.dayHour < 7);
            System.out.println("Кафе відкривається");
            isOpen = true;
            baristaThread.start();

            int clientId = 1;
            while (isOpen) {
                if(TimeRunnable.dayHour > 18){
                    isOpen = false;
                    break;
                }
                Thread clientThread = new Thread(new Client(), "Client " + clientId++);
                allDayClients.add(clientThread);
                clientThread.start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Кафе закрилось");
            baristaThread.interrupt();

            try {
                timeThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread cafeThread = new Thread(cafe, "cafeThread");
        cafeThread.start();
        cafeThread.join();
        for (Thread client : allDayClients) {
            client.join();
        }
    }
}




