package cinema.demo;

class Main {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("the thread start");
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the thread end");
            }
        });
        t.start();
        System.out.println("throw exception in main");
        int x = 5/0;
        System.out.println("after exception in main");
    }
}
