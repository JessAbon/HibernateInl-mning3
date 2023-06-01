package se.yrgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import se.yrgo.Address;

@Entity

public class Student { // @Transient för att det inte ska bli en kolumn i tabellen

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String enrollmentID;
    private String name;
    private Integer numberOfCourses;

    @Embedded
    private Address address;

    public Student() {
    }

    public Student(String name, String enrollmentID, String street, String city, String zipcode) {
        this.name = name;
        this.enrollmentID = enrollmentID;
        this.address = new Address(street, city, zipcode);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "NUM_COURSES")
    public Integer getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(Integer numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " lives at:  " + address;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (enrollmentID == null) {
            if (other.enrollmentID != null)
                return false;
        } else if (!enrollmentID.equals(other.enrollmentID))
            return false;
        return true;
    }

}
