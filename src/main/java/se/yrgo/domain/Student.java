package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
//@Table(name = "TBL_STUDENT")

public class Student    {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

    private String enrollmentID;
    private String name;

    @Column(name = "NUM_COURSES")
    private Integer numberOfCourses;
    @Transient
    private String getEmail;

    @Embedded
    private Address address;


    public Student() {
    }

    public Student(String name, String enrollmentID) {
        this.name = name;
        this.enrollmentID = enrollmentID;

//        this.tutor = tutor;
    }


    //And a constructor:
    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }

//    @Override
//    public void getReport() {
//        System.out.println("Report for student: " + this.getName());
//    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }



//    public Student(String name, String enrollmentID, String street, String city,
//                   String zipcode){
//        this.name = name;
//        this.enrollmentID= enrollmentID;
//        this.street = street;
//        this.city = city;
//        this.zip = zipcode;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return enrollmentID.equals(student.enrollmentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentID);
    }

    //    public Tutor getTutor() {
////        return tutor;
//    }

//    public int getId() {
//        return id;
//    }

//    public void setTutor(String tutorName) {
//        this.tutor = tutor;
//    }

//    public void allocateTutor(Tutor tutor) {
//        this.tutor=tutor;
//    }




    public String getName() {
        return name;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }
    public String toString() {
        return name + " lives at: " + address;
    }


}

