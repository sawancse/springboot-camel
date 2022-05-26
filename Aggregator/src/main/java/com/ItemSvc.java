package com;

import com.model.Item;

public class ItemSvc {
    
    public Item processBook(Item item) throws InterruptedException {
        
        System.out.println("handle book Item:" +item);
        item.setPrice(30);
        
        System.out.println("book Item processed");
        
        return item;
    }

    public Item processPhone(Item item) throws InterruptedException {
        
        System.out.println("handle phone Item:" +item);
        item.setPrice(500);
        
        System.out.println("phone Item processed");
        
        return item;
    }
}
