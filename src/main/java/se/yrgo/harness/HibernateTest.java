


package se.yrgo.harness;

import jakarta.persistence.*;

import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;

public class HibernateTest
{
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args){
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // Task 1
        Subject science = em.find(Subject.class, 2);
        Query query= em.createQuery("select tutor.teachingGroup  from Tutor tutor where :subject member of tutor.subjectsToTeach");
        query.setParameter("subject", science);
        List<Student>tutorsForSience = query.getResultList();
        for(Student student : tutorsForSience) {
            System.out.println(student + "\n");
        }
        // Task 2
        Query query2 = em.createQuery("Select student.name, tutor.name from Tutor as tutor join tutor.teachingGroup as student");
        List<Object[]> results = query2.getResultList();
        for (Object[] item : results) {
            System.out.println("Student: " + item[0] + " teacher: " + item[1]);
            System.out.println("");

        }
        // Task 3
        double averageSemester = (double)em.createQuery("select avg(subject.numberOfSemesters) from Subject as subject").getSingleResult();
        System.out.println("The average length of semesters is " + averageSemester + " semesters");


        // This is just a test query
        //        Integer maxSalary = (Integer) em.createQuery("select teacher.salary from Tutor as teacher order by teacher.salary desc").setMaxResults(1).getSingleResult();
        //        System.out.println("The biggest salary a teacher has is: " + maxSalary + " sek");


        // Task 4
        Integer maxSalary = (Integer) em.createQuery("select max(teacher.salary) from Tutor as teacher").getSingleResult();
        System.out.println("The biggest salary a teacher has is: " + maxSalary + " sek");


        // Task 5
        List<Tutor> teachers = em.createNamedQuery("searchBySalary", Tutor.class).setParameter("salary", 10000).getResultList();
        for(Tutor tutor: teachers){
            System.out.println(tutor);
        }




        tx.commit();
        em.close();
    }

    public static void setUpData(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        Subject gamesOfThrones = new Subject("Game of Thrones", 7);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);
        em.persist(gamesOfThrones);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);




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
        t2.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");


        tx.commit();
        em.close();
    }


}