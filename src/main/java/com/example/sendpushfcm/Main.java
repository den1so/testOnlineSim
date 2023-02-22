package com.example.sendpushfcm;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

       byte b = (byte)200;


       int[] arr = new int[]{1,1,1};

        ///////

//        onlineSIM oSim = new onlineSIM();
//
//        Scanner sc = new Scanner(System.in);
//        int choice=0;
//        System.out.println("Menu:");
//        System.out.println("1. Узнать баланс");
//        System.out.println("2. Получить номер");
//        System.out.println("3. Получть СМС");
//        System.out.println("4. Quit");
//        while(choice!=5) {
//
//            System.out.print("Enter your choice: ");
//            choice = sc.nextInt();
//            switch (choice) {
//                case 1:
//                    //System.out.println("You chose option 1");
//                    oSim.getBalance();
//                    System.out.println("Ваш баланс: " + oSim.getUserBalance());
//                    break;
//                case 2:
//                    //System.out.println("You chose option 2");
//                    String serv = "miratorg";
//                    oSim.getNumber(serv);
//                    break;
//                case 3:
//                    //System.out.println("You chose option 3");
//                    oSim.getState(oSim.getTzid());
//                    break;
//                case 5:
//                    System.out.println("Quitting");
//                    break;
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        }

//        oSim.getBalance();
//
//        //oSim.getNumber("av100_ru");
//        System.out.println("Давайте проверим баланс: " + oSim.getUserBalance());
//        System.out.println("Давайте проверим tzid: " + oSim.getTzid());
//        System.out.println("Хочу список всех сервисов:");
//        oSim.getNumberStats();


//
//        Scanner sc = new Scanner(System.in);
//        String s1;
//        while(sc.hasNextLine()){
//            s1 = sc.nextLine();
//            if(s1.equals("1")) {
//                oSim.getState(oSim.getTzid());
//            }
//        }



    }
}
