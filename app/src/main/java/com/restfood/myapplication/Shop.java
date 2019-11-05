package com.restfood.myapplication;

public class Shop {
    private  String shop_name ;
    private  String shop_id;
    private  String shop_type;
    private  String shop_address;
    private String  shop_email;


public void setShopName(String name)
{
    shop_name=name ;
}

public String getShopName()
{
    return shop_name;
}

public void  setshop_id(String Id) {
    shop_id = Id;
}


public void setShop_type(String type){
    shop_type =type;
  }
 public String getShop_type(){
    return shop_type;
 }
 public String getShopId()
 {
     return shop_id;
 }

 public String getShopAddress()
 {
     return shop_address;
 }
 public void setShopEmail(String email){
    shop_email =email;
    }
    public String getShopemail(){
    return shop_email;
    }
}
