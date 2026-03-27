/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javapaper;
import java.sql.*;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author Admin
 */

class  patitent 
{
    String name;
    int age;
    int oxy;
    int hrct;
    String complain;

patitent(String name,int age,int oxy,int hrct,String complain)
{
    this.name=name;
    this.age=age;
    this.oxy=oxy;
    this.hrct=hrct;
    this.complain=complain;
} 
}

class pexception extends Exception
{
    public pexception(String msg)
    {
        super(msg);
    }
}
 class covidcounter extends Thread
 {
     static int count=0;
     
     public void run()
     {
         try
         {
             while(true)
             {
                 Thread.sleep(120000);
                 System.out.println("covid count:"+count);
             }
         }
         catch(Exception e)
         {
             System.out.println(e);
         }
     }
 }

public class Javapaper {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/hospital",
                     "root",
                     ""
             );
             System.out.println("connection succesfull");
           
             covidcounter t = new covidcounter();
             t.setDaemon(true);
             t.start();
             
             int choice;
             do
             {
                 System.out.println("1.insert");
                 System.out.println("2.display");
                 System.out.println("3.exit");
                 
                 System.out.println("enter choice");
                 choice =sc.nextInt();
                 
                 switch(choice)
                 {
                     case 1:
                         System.out.println("enter name");
                         String name=sc.next();
                         
                         System.out.println("enter age");
                         int age=sc.nextInt();
                         
                         System.out.println("enter oxygen leavle");
                         int oxy=sc.nextInt();
                         
                         System.out.println("enter hrct");
                         int hrct=sc.nextInt();
                         sc.nextLine();
                         System.out.println("ENTER complaint");
                         String comp = sc.next();
                         
                         FileWriter fw = new FileWriter("pa.txt",true);
                         fw.write(oxy+" "+hrct+" "+comp);
                         fw.close();
                          
                         if(oxy<95 && hrct >10)
                         {
                             covidcounter.count++;
                             throw new pexception("patitent is corona positive");
                         }
                         else
                         {
  System.out.println("Patient Info:");
                            System.out.println(name + " " + age + " " + oxy + " " + hrct + " " + comp);
                         }
                        String insert = "insert into patient(name,age,oxy,hrct,complain) values(?,?,?,?,?)";
                    PreparedStatement ps = con.prepareStatement(insert);
                    ps.setString(1, name);
                    ps.setInt(2, age);
                    ps.setInt(3, oxy);
                    ps.setInt(4, hrct);
                    ps.setString(5, comp);
                    ps.executeUpdate();

                    System.out.println("Record Inserted in DB");
                    break;
                    
                     case 2:
                        String query = "select * from patient";
                       Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                   System.out.println("\nPatient List:");
      while (rs.next()) {
                        System.out.println(
                                rs.getString("name") + " " +
                                rs.getInt("age") + " " +
                                rs.getInt("oxy") + " " +
                                rs.getInt("hrct") + " " +
                                rs.getString("complain")
                        );
                    }
                   break;
                   
                     case 3:
                                            System.out.println("Exiting...");
                             break;
                 }
             }while(choice != 0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("connection faild");
        }
         }
    
}
