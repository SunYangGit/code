package com.test.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CreateThread {


    public static void main(String[] args) throws InterruptedException {
        Car car = new Car("car runnable permission");
        car.start();

        Thread thread = new Thread(new Working(),"car Thread  permission");
        thread.start();

        FutureTask<String> futureTask = new FutureTask<>(new CallingWork());
        Thread taskThread = new Thread(futureTask);

        for (int i = 0 ; i < 10; i++){
            System.out.println(taskThread.getState());
            if (i == 0){
                taskThread.start();
            }

            if (i ==1){
                futureTask.cancel(true);
            }

            System.out.println(taskThread.getState());
            Thread.sleep(5*1000);
            System.out.println(taskThread.getState());
        }

    }


    public static class  Car  extends  Thread{

        private  String name;

        public Car(String name){
            super(name);
        }

        @Override
        public void run() {
            System.out.println("Car is running ... . thread name ："+ Thread.currentThread().getName());
        }
    }



    public static class Working  implements  Runnable{
        @Override
        public void run() {
            System.out.println("Working is running ... . thread name ："+ Thread.currentThread().getName());
        }
    }

    public static  class  CallingWork implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("CallingWork is running ... . thread name ："+ Thread.currentThread().getName());
            for (int i = 0 ; i < 5; i++){
               Thread.sleep(2*1000);
            }
            return "SUCCESS";
        }
    }

}
