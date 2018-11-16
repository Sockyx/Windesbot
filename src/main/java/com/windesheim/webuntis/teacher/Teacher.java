package com.windesheim.webuntis.teacher;

import org.json.JSONArray;

/**
 * @author Lucas Ouwens
 */
public class Teacher {

    private String teacherCode;
    private String name;

    private Teacher(String teacherCode, String teacherName) {
        this.teacherCode = teacherCode;
        this.name = teacherName;
    }

    public static Teacher _new(String teacherCode, String teacherName) {
        return new Teacher(teacherCode, teacherName);
    }

    public String getName() {
        return name;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public static Teacher[] multipleFromJSONArray(JSONArray teachers) {
        Teacher[] oTeachers = new Teacher[teachers.length()];
        for(int i = 0; i < teachers.length(); i++) {
            oTeachers[i] = Teacher._new(null, teachers.get(i).toString());
        }

        return oTeachers;
    }

}
