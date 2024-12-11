public class Main {
    public static void main(String[] args) throws InterruptedException {

        // [+][+][+][+] - поток 1
        // [-][-][-][-] - поток 2
        // [+][-][+][-][+]......синхронизация!!!
        MyThread thread1 = new MyThread("+");
        thread1.start();
        MyThread thread2 = new MyThread("-");
        thread2.start();
        Thread.sleep(1000);
        thread2.flag = false;
        thread2.join(); // присоед. и ждет завершения потока
        printMessage("Thread 2 is stopped!");

    }

    public static Object key = new Object();

    public static void printMessage(String m) {

        synchronized (key) {
            try {
                System.out.print("[");
                System.out.print(m);
                System.out.print("]");
                Thread.sleep(300);
               // key.notify(); // возобновляет работу потока в режиме wait
              //  key.wait(); // ставить поток в режим wait
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
class MyThread extends Thread {
    public String message;
    public boolean flag = true;
    MyThread(String message) {
        this.message = message;
    }
    @Override
    public void run() {
        while(flag) {
            Main.printMessage(this.message);
        }
    }
}
