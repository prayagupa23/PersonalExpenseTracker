package model;

import java.util.Date;

public class Expense {
    private double amount;
    private String category;
    private Date date;
    private String description;

    public Expense(){}

    //getters
    private double getAmount(){return amount;}
    private String getCategory(){return category;}
    private Date getDate(){return date;}
    private String getDescription(){return description;}

    //setters
    public void setAmount(double amount){this.amount = amount;}
    public void setCategory(String category){this.category = category;}
    public void setDate(Date date){this.date = date;}
    public void setDescription(String description){this.description = description;}
}
