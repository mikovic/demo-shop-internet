package com.mikovic.demoshopinternet.unit;

import com.mikovic.demoshopinternet.entities.OrderItem;
import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.utils.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestShoppingCart {

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[] {new Product(0L,"tv","OK",10000),
                new Product(1L,"tv2","Bad",20000)};
    }

    private Product product;



    public TestShoppingCart(Product product) {
        this.product = product;

    }

    ShoppingCart cart;
    Product firstProduct;

    @Before
    public void init() {
        System.out.println("init cart and first product for her");
        cart = new ShoppingCart();
        firstProduct =   new Product(2L,"combain","OK",5000);

    }
    @Test
    public void testAdd1() {
                cart.add(firstProduct);
        Assert.assertEquals(java.util.Optional.of(5000.0).orElse(5000.0), cart.getTotalCost());
        cart.add(product);
        Assert.assertEquals(java.util.Optional.of(15000.0).orElse(15000.0), cart.getTotalCost());
        cart.add(product);
        Assert.assertEquals(java.util.Optional.of(25000.0).orElse(25000.0), cart.getTotalCost());


    }

    @Test
    public void testAdd2() {
        cart.add(product);
        cart.add(product);
        for(OrderItem o: cart.getItems()){
            Assert.assertEquals("tv", o.getProduct().getTitle());
            Assert.assertEquals(java.util.Optional.of(2L).orElse(2L), o.getQuantity());
        }


    }

    @Test
    public void testAdd3() {
        cart.add(firstProduct);
        cart.add(firstProduct);
        cart.add(product);
        cart.add(product);
        cart.remove(product);
        cart.remove(product);
        Assert.assertEquals(java.util.Optional.of(10000.0).orElse(10000.0), cart.getTotalCost());
        Assert.assertEquals(1, cart.getItems().size());
        Assert.assertEquals(java.util.Optional.of(2L).orElse(2L), cart.getItems().get(0).getQuantity());


    }
}
