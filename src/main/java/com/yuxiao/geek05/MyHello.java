package com.yuxiao.geek05;

/**
 * @author yangjunwei
 * @date 2021-08-06 21:22
 */
public class MyHello {


    public static void main(String[] args) {
        int a = 1;
        int b = a << 3;

        int c = a * b;
        boolean bool = c <= b;
        System.out.println(" b = " + b);
        if (bool) {
            System.out.println("c less than b");
        }

        byte by = 1;

        float fl = 1.1f;

        double dou = 0.2d;

        System.out.println("by + a =" + (by + a));

        System.out.println("fl * dou = " + fl*dou);

        System.out.println("by - fl = " + (by - fl));
    }

}
