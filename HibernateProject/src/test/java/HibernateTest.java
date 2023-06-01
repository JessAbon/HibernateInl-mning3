
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

public class HibernateTest {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

	public static void main(String[] args) {
		setUpData();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		/* Task 1 */
		Subject science = em.find(Subject.class, 2);
		TypedQuery<Student> query = em.createQuery(
				"select student from Tutor as tutor join tutor.teachingGroup as student where :subject member of tutor.subjectsToTeach",
				Student.class);
		query.setParameter("subject", science);

		List<Student> studentGroup = query.getResultList();
		System.out.println("Students whos Tutor who can teach science is: ");
		for (Student student : studentGroup) {
			System.out.println(student);
		}

		/* Task 2 */
		List<Object[]> results = em.createQuery(
				"select student.name, tutor.name from Tutor tutor join tutor.teachingGroup student")
				.getResultList();
		for (Object[] obj : results) {
			String studentName = (String) obj[0];
			String tutorName = (String) obj[1];
			System.out.println("Student Name: " + studentName);
			System.out.println("Their Tutor's Name: " + tutorName);
		}

		/* Task 3 */
		double avgSemesters = (Double) em.createQuery("select avg(numberOfSemesters)from Subject subject")
				.getSingleResult();
		System.out.println("Average semester length: " + avgSemesters);

		/* Task 4 */
		int maxSalary = (int) em.createQuery("select max(salary)from Tutor tutor")
				.getSingleResult();
		System.out.println("Max salary: " + maxSalary);

		/* Task 5 */
		List<Tutor> salaryList = em.createNamedQuery("salaryHigher", Tutor.class).setParameter("salary", 10000)
				.getResultList();
		System.out.println("Tutors with salary higher than 10000");
		for (Tutor tutor : salaryList) {
			System.out.println(tutor);
		}

		tx.commit();
		em.close();
	}

	public static void setUpData() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Subject mathematics = new Subject("Mathematics", 2);
		Subject science = new Subject("Science", 2);
		Subject programming = new Subject("Programming", 3);
		em.persist(mathematics);
		em.persist(science);
		em.persist(programming);

		Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
		t1.addSubjectsToTeach(mathematics);
		t1.addSubjectsToTeach(science);

		Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
		t2.addSubjectsToTeach(mathematics);
		t2.addSubjectsToTeach(science);

		// This tutor is the only tutor who can teach History
		Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
		t3.addSubjectsToTeach(programming);

		em.persist(t1);
		em.persist(t2);
		em.persist(t3);

		t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
		t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
		t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

		tx.commit();
		em.close();
	}

}
