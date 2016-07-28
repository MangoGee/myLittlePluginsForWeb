public class GetPhoneNum {
  public static void main(String[] args) {
      Thread thread1 = new Thread(new MyThread(), "A");
      Thread thread2 = new Thread(new MyThread(), "B");
      Thread thread3 = new Thread(new MyThread(), "C");
      Thread thread4 = new Thread(new MyThread(), "D");
      Thread thread5 = new Thread(new MyThread(), "E");
      Thread thread6 = new Thread(new MyThread(), "F");
      Thread thread7 = new Thread(new MyThread(), "G");
      Thread thread8 = new Thread(new MyThread(), "H");
      Thread thread9 = new Thread(new MyThread(), "I");
      Thread thread10 = new Thread(new MyThread(), "J");
      thread1.start();
      thread2.start();
      thread3.start();
      thread4.start();
      thread5.start();
      thread6.start();
      thread7.start();
      thread8.start();
      thread9.start();
      thread10.start();
  }
}


