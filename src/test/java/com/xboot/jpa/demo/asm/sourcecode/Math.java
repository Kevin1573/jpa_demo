package com.xboot.jpa.demo.asm.sourcecode;

public class Math {
    private Integer num1;

    public Math() {
    }

    public int add(int a, int b) {
        return a + b + this.num1;
    }

    public void setNum1(Integer num1){
        this.num1 = num1;
    }
}