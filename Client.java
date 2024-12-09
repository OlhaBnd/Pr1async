class Client implements Runnable {
    @Override
    public void run() {
        if(!Main.isOpen || Main.waiterPlaces.availablePermits() == 0) return;
        try {
            System.out.println(Thread.currentThread().getName() + " заходить до кафе");
            Main.waiterPlaces.acquire();
            Main.orderPlaces.release();
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + " зробив замовлення");
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}