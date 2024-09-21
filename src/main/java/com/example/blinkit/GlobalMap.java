package com.example.blinkit;

import android.location.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalMap {

    public static Map<String,String>map=new HashMap<>();
    public static List<GroceryModel> groceryModel=new ArrayList<>();

    public static List<GroceryModel> orderModel=new ArrayList<>();

    public static HashMap<Integer,Integer> orderList=new HashMap<>();

    public static int totalPrice=0;

    public static int itemSelected=0;

    public static String addressDefault;

    public static double finalPrice=0;

    public static String email=null;

}
