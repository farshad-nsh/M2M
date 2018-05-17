package com;
import java.io.*;
import java.lang.*;
import java.util.*;

public class createFile {
    private Formatter x;
    public String message;
    public void openFile(){
        try{
            x=new Formatter("curl.json");
        }
        catch(Exception e){
            System.out.println("you have a nice error!");
        }
    }
    public void addRequest(String message){
        x.format("%s",message);

        System.out.println("inside addRequest method in createFile class!");
    }
    public void closeFile(){
        x.close();
    }
}

