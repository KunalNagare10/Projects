package com.jspiders.cardekhousingjdbc.caroperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.jspiders.cardekhousingjdbc.car.Car;

public class CarOperations {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String query;
	// Should contain methods for adding, removing, updating and retrieving a car

	ArrayList<Car> carList = new ArrayList<>();

	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardekho", "root", "root");
		return connection;
	}

	public void addCar(Car car) {
		try {
			connection = openConnection();
			query = "INSERT INTO cars VALUES (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, car.getId());
			preparedStatement.setString(2, car.getName());
			preparedStatement.setString(3, car.getBrand());
			preparedStatement.setDouble(4, car.getPrice());
			preparedStatement.setString(5, car.getFuelType());

			int row = preparedStatement.executeUpdate();
			System.out.println(row + " row(s) affected");

			carList.add(car);
			System.out.println("Car added successfully.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Car> getAllCars() {
		ArrayList<Car> carList = new ArrayList<>();
		try {
			connection = openConnection();
			query = "SELECT * FROM cars";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Car car = new Car();
				car.setId(resultSet.getInt(1));
				car.setName(resultSet.getString(2));
				car.setBrand(resultSet.getString(3));
				car.setPrice(resultSet.getDouble(4));
				car.setFuelType(resultSet.getString(5));
				carList.add(car);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return carList;
	}

	public Car getCarById(int id) {
		Car car = new Car();
		try {
			connection = openConnection();
			query = "SELECT * FROM cars WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				car.setId(resultSet.getInt(1));
				car.setName(resultSet.getString(2));
				car.setBrand(resultSet.getString(3));
				car.setPrice(resultSet.getDouble(4));
				car.setFuelType(resultSet.getString(5));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return car;
	}

	public void deleteCar(int deleteId) {

		try {
			connection = openConnection();
			query = "DELETE FROM cars WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, deleteId);
			int row = preparedStatement.executeUpdate();
			System.out.println(row + " row(s) affected");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateCar(int idtoUpdate) {
		Scanner scanner = new Scanner(System.in);
		try {
			connection = openConnection();
			query = "UPDATE cars SET name=?,brand=?,price=?,fuelType=? WHERE id=?";
			preparedStatement = connection.prepareStatement(query);

			System.out.println("Enter Name Of Car.");
			String model = scanner.nextLine();
			preparedStatement.setString(1, model);

			System.out.println("Enter Brand Of Car.");
			String company = scanner.nextLine();
			preparedStatement.setString(2, company);

			System.out.println("Enter Price Of Car.");
			double price = scanner.nextDouble();
			scanner.nextLine();
			preparedStatement.setDouble(3, price);

			System.out.println("Enter Fuel Type Of Car.");
			String fuel = scanner.nextLine();
			preparedStatement.setString(4, fuel);

			preparedStatement.setInt(5, idtoUpdate);

			int row = preparedStatement.executeUpdate();
			System.out.println(row + " row(s) affected");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeConnection() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}

	}

}
