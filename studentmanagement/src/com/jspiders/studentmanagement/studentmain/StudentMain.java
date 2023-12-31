package com.jspiders.studentmanagement.studentmain;

import java.util.List;
import java.util.Scanner;

import com.jspiders.studentmanagement.student.Student;
import com.jspiders.studentmanagement.studentmanage.StudentManage;

public class StudentMain {

	public static void main(String[] args) {

		StudentManage studentManage = new StudentManage();
		Scanner scanner = new Scanner(System.in);
		boolean flag = true;

		while (flag) {
			System.out.println(
					"Enter 1 to add student.\nEnter 2 to get all students.\nEnter 3 to get student by id.\nEnter 4 to delete student.\nEnter 5 to update student.\nEnter 6 to Exit.");
			System.out.println("Enter your choice.");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				Student student = new Student();
				System.out.println("Enter student id.");
				student.setId(scanner.nextInt());
				scanner.nextLine();
				System.out.println("Enter student name.");
				student.setName(scanner.nextLine());
				System.out.println("Enter student email.");
				student.setEmail(scanner.nextLine());
				System.out.println("Enter student age.");
				student.setAge(scanner.nextInt());
				System.out.println("Enter student fees.");
				student.setFees(scanner.nextDouble());
				studentManage.addStudent(student);
				break;
			case 2:
				List<Student> students = studentManage.getAllStudents();
				for (Student s : students) {
					System.out.println(s);
				}
				break;
			case 3:
				System.out.println("Enter student id.");
				Student s = studentManage.getStudentById(scanner.nextInt());
				System.out.println(s);
				break;
			case 4:
				System.out.println("Enter student id.");
				studentManage.deleteStudent(scanner.nextInt());
				break;
			case 5:
				System.out.println("Enter student id.");
				studentManage.updateStudent(scanner.nextInt(), scanner);
				break;
			case 6:
				System.out.println("Thank you!");
				flag = false;
				break;
			default:
				System.out.println("Invalid choice.");
			}
		}
		scanner.close();
	}

}