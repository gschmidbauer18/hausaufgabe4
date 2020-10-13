/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hausaufgabe4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author 43676
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        List<Integer> ints =new ArrayList<>();
        
        try {
            BufferedReader br =new BufferedReader(new FileReader("numbers.csv"));
            String s=br.readLine();
            while(s!=null)
            {
                String[] sarr=s.split(":");
                List<String> slist=new ArrayList<>();
                
                for(int i=0;i<sarr.length;i++)
                {
                    slist.add(sarr[i]);
                }
                
                for(int i=0;i<slist.size();i++)
                {
                    try
                    {
                        int integer=Integer.parseInt(slist.get(i));
                        ints.add(integer);
                    }
                    catch(NumberFormatException e)
                    {
                        //System.out.println("Exception no Int");
                    }
                }
                s=br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //ints.stream().forEach(i -> System.out.println(i));
        System.out.println("Teiler eingeben:");
        String teilerstring=sc.nextLine();
        int teiler=Integer.parseInt(teilerstring);
        
        System.out.println("Chunks eingeben:");
        String chunkstring=sc.nextLine();
        int chunks=Integer.parseInt(chunkstring);
        
        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(chunks);
        int chunk=ints.size()/chunks;
        
        for(int i=0;i<chunks;i=i=i+chunk)
        {
            Runnable r=new Runnable() {
                @Override
                public void run() {
                    for(int k=0;k<chunk;k++)
                    {
                        if(ints.get(k)%teiler==0)
                        {
                            System.out.println(ints.get(k)+" ist ein Teiler von "+teiler);
                        }
                    }
                }
            };
            
            executor.execute(r);
        }
        executor.shutdown();
        
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(" ");
        System.out.println("Nummer eingeben:");
        String numberstring=sc.nextLine();
        int number=Integer.parseInt(numberstring);
        int number100=100;
        int gesamtSumme=0;
        
        ExecutorService executor2 = Executors.newFixedThreadPool(number/100+1);
        List<Callable<Integer>> callList= new ArrayList();
        
        for(int i=0;i<number;i=i=i+number100)
        {
            final int actualNumber=i;
            Callable r=new Callable() {
                @Override
                public Object call() throws Exception {
                    //int gesamtsumme=0;
                    int zwischensumme=0;
                    if(actualNumber+100>number)
                    {
                        int rest=number-actualNumber;
                        for(int k=actualNumber+1;k<=actualNumber+rest;k++)
                        {
                            zwischensumme=zwischensumme+k;
                            //gesamtsumme=gesamtsumme+zwischensumme;
                        }
                        System.out.println("Summe der Zahlen "+actualNumber+" - "+(actualNumber+rest)+" = "+zwischensumme);
                        return zwischensumme;
                    }
                    else
                    {
                    for(int k=actualNumber+1;k<=actualNumber+number100;k++)
                    {
                        zwischensumme=zwischensumme+k;
                        //gesamtsumme=gesamtsumme+zwischensumme;
                    }
                    System.out.println("Summe der Zahlen "+actualNumber+" - "+(actualNumber+100)+" = "+zwischensumme);
                    return zwischensumme;
                    }
                    }
            };
            
            callList.add(r);
        }
        
        try {
            List<Future<Integer>> futureList= executor2.invokeAll(callList, 10, TimeUnit.SECONDS);
            Thread.sleep(5000);
            int summe=0;
            for (int i = 0; i < futureList.size(); i++) {
                summe=summe+futureList.get(i).get();
            }
            System.out.println("Gesamtsumme = "+summe);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor2.shutdown();
        
        
    }
    
}
