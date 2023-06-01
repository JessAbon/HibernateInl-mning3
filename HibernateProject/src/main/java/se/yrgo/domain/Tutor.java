package se.yrgo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor {

    @OneToMany(cascade = CascadeType.PERSIST)
    @MapKey(name = "enrollmentID")
    @JoinColumn(name = "TUTOR_FK")
    private List<Student> teachingGroup;

    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    @Column(unique = true, nullable = false)
    private String tutorId;
    private String name;
    private int salary;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId = tutorId;
        this.name = name;
        this.salary = salary;
        this.teachingGroup = new ArrayList<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    public Tutor() {

    }

    public void createStudentAndAddtoTeachingGroup(String name, String enrollmentID, String street, String city,
            String zipcode) {
        Student student = new Student(name, enrollmentID, street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        /* this.teachingGroup.put(newStudent.getEnrollmentID(), newStudent); */
        this.teachingGroup.add(newStudent);
    }

    public List<Student> getTeachingGroup() {
        List<Student> unmodifiable = Collections.unmodifiableList(this.teachingGroup);
        return unmodifiable;
    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public Set<Subject> getSubjects() {
        return this.subjectsToTeach;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Tutor with tutorId: " + tutorId + ", name: " + name + ", salary: " + salary;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tutorId == null) ? 0 : tutorId.hashCode());
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
        Tutor other = (Tutor) obj;
        if (tutorId == null) {
            if (other.tutorId != null)
                return false;
        } else if (!tutorId.equals(other.tutorId))
            return false;
        return true;
    }

}
