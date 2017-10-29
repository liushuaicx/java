package com.kaishengit.proxy;

public class Proxy implements Sales {

//    private Asus asus;
    private Sales sales;

    public Proxy(Sales sales) {
        this.sales = sales;
    }

    @Override
    public void salePC() {
//        asus.salePC();
        sales.salePC();
    }
}
