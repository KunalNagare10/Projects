package com.jspiders.cardekhousingjdbc.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.jspiders.cardekhousingjdbc.car.Car;
import com.jspiders.cardekhousingjdbc.caroperations.CarOperations;


public class CarMain {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		CarOperations carOperations = new CarOperations();
		boolean check=true;
		while (check) {
			System.out.println(
					"Enter 1 to add car.\nEnter 2 to get all cars.\nEnter 3 to get car by id.\nEnter 4 to delete car.\nEnter 5 to update car.\nEnter 6 to Exit.");
			System.out.println("Enter your choice.");
            int choice = scanner.nextInt();
            
            switch (choice) {
            case 1:
            	Car car=new Car();
                System.out.println("Enter car details.");
                System.out.println("Enter Car id.");
                car.setId(scanner.nextInt());
                scanner.nextLine();
                
                System.out.println("Enter Name Of Car.");
                car.setName(scanner.nextLine());
                
                System.out.println("Enter Brand Of Car.");
                car.setBrand(scanner.nextLine());
                
                System.out.println("Enter Price Of Car.");
                car.setPrice(scanner.nextDouble());
                scanner.nextLine();
                
                System.out.println("Enter Fuel Type Of Car.");
                car.setFuelType(scanner.nextLine());
                carOperations.addCar(car);
                
                break;
            case 2:
            	  ArrayList<Car> cars = carOperations.getAllCars();
            	  for (Car c : cars) {
            		  System.out.println(c);
            		  }
            	  break;

            case 3:
                System.out.println("Enter car id.");
                int id = scanner.nextInt();
                Car c=carOperations.getCarById(id);
                System.out.println(c);
                break;
            case 4:
                System.out.println("Enter car id:");
                int deleteId = scanner.nextInt();
                carOperations.deleteCar(deleteId);
                break;
            case 5:
                System.out.println("Enter car id:");
                int idtoUpdate = scanner.nextInt();
                carOperations.updateCar(idtoUpdate);
                break;
            case 6:
            	check=false;
            	System.out.println("Thank you :) and Visit Again");
                scanner.close();
              
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                break;
            }
		}
		scanner.close();
	}
}

