package com.windesheim.webuntis.subject;

/**
 * @author Lucas Ouwens
 */
public class Subject {

    private String subjectCode;
    private String name;

    private Subject(String subjectCode, String subjectName) {
        this.subjectCode = subjectCode;
        this.name = subjectName;
    }

    public static Subject _new(String subjectCode, String subjectName) {
        return new Subject(subjectCode, subjectName);
    }

    public String getName() {
        return name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }
}
