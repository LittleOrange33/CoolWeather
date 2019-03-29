package com.example.admin.helloworld;

import java.util.Arrays;
import java.util.Collection;

public class MoreThreads {
    public static void main (String args[]) {
        final String Demo[] = {
                "zero","one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"
        };

            Thread thread = new Thread(){
            @Override
            public void run() {
                //System.out.println("Hello world two!");
            }
        };
            thread.start();
            //System.out.println("Hello world one!");

        Collection<String> list = java.util.Arrays.asList(Demo);
        for(String str:list){
        final String  str1 = str;
            (new Thread() {
            //

            @Override
            public void run() {

                    System.out.println("Hello world " + str1);

            }
        }).start();
    }

    }
}
