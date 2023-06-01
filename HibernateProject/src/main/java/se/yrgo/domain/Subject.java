package se.yrgo.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany
    private Set<Tutor> tutors;

    private String subjectName;
    private int numberOfSemesters;

    public Subject(String subjectName, int numberOfSemesters) {
        this.subjectName = subjectName;
        this.numberOfSemesters = numberOfSemesters;
        this.tutors = new HashSet<Tutor>();
    }

    public Subject() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void addTutorToSubject(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getSubjects().add(this);
    }

    public Set<Tutor> getTutors() {
        return this.tutors;
    }

}
