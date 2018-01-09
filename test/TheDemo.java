package test;

public class TheDemo {
    public synchronized void test(String name) {
        for(int i=0;i<3;i++) {
            print(name + " :: "+i);
            try{
                Thread.sleep(500);
            } catch (Exception e) {
                print(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        TheDemo theDemo = new TheDemo();
        new TestThread("THREAD 1",theDemo);
        new TestThread("THREAD 2",theDemo);
        new TestThread("THREAD 3",theDemo);
    }

    public static void print(String s) {
        System.out.println(s);
    }
}