class TimeRunnable implements Runnable {
    public static volatile int dayHour = 0;

    @Override
    public void run() {
        while (dayHour < 24) {
            System.out.printf("%2d година\n", dayHour);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            dayHour++;
        }
        System.out.println("Кінець дня.");
    }
}