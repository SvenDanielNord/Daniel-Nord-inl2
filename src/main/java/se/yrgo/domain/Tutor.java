package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity

public class Tutor {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String tutorId;
    private int salary;


    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;
    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    public Tutor() {

    }

    public Tutor(String tutorId, String name, int salary) {

        this.name = name;
        this.tutorId = tutorId;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();

    }


    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public Set<Student> getTeachingGroup() {
        Set<Student> unmodifiable =
                Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public void createStudentAndAddtoTeachingGroup(String studentName,
                                                   String enrollmentID, String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }

    public void addSubjectsToTeach(Subject subject) {
        subject.getTutors().add(this);
        this.subjectsToTeach.add(subject);
    }

    public Set<Subject> getSubjects() {
        return this.subjectsToTeach;
    }

    public String getTutorId() {
        return tutorId;
    }



    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Tutor:" +
                "tutorId'" + tutorId + '\'' +
                ", name:'" + name + '\'' +
                ", salary:" + salary;
    }
}
