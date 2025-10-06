class Odd extends Thread {
    int n;
    Odd(int n) {
        this.n = n;
    }
    public void run() {
        System.out.println("Odd Numbers:");
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.println(i);
            }
        }
    }
}

class Even extends Thread {
    int n;
    Even(int n) {
        this.n = n;
    }
    public void run() {
        System.out.println("Even Numbers:");
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }
}

public class Thread {
    public static void main(String[] args) {
        int n = 10; 

        Odd t1 = new Odd(n);
        Even t2 = new Even(n);

        t1.run();
        t2.run();
    }
}
