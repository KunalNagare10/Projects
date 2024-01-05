package com.jspiders.smswithspringmvc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspiders.smswithspringmvc.pojo.StudentPOJO;

@Component
public class StudentRepository {
	
	@Autowired
	private EntityManager entityManager;
	
	public void addStudent(StudentPOJO studentPOJO) {
		System.out.println("repository addStudent()");
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.persist(studentPOJO);
		entityTransaction.commit();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<StudentPOJO> getAllStudents() {
		System.out.println("repository getAllStudent()");
		
		Query query = entityManager.createQuery("SELECT student FROM StudentPOJO student");
		return query.getResultList();
		}
	
	public void deleteStudent(StudentPOJO studentPOJO) {
		System.out.println("repository deleteStudent()");
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		entityManager.remove(studentPOJO);
		entityTransaction.commit();
	}

	public StudentPOJO getStudentById(int id) {
		return entityManager.find(StudentPOJO.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<StudentPOJO> searchStudentByPattern(String pattern) {
		Query query= entityManager.createQuery("SELECT student FROM StudentPOJO student "
				+ "WHERE name LIKE '%"+pattern+"%'");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StudentPOJO> getAllStudentsInOrder() {
		Query query = entityManager.createQuery("SELECT student FROM StudentPOJO student ORDER BY student.age");
		return query.getResultList();
	}

}
