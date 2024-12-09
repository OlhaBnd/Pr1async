
class Barista implements Runnable {
    @Override
    public void run() {
        System.out.println("Бариста почав роботу");
        int i = 1;
        while (Main.isOpen || Main.orderPlaces.tryAcquire()) {
            try {
                Main.orderPlaces.acquire();
                Main.waiterPlaces.release();
                Thread.sleep(300);
                System.out.printf("Бариста виконав замовлення #%2d\n", i++);
            } catch (InterruptedException e) {
                System.out.println("Бариста закінчив роботу");
                // throw new RuntimeException(e);
            }
        }
    }
}
