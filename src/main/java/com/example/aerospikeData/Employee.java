package com.example.aerospikeData;

import java.util.Objects;

public class Employee {
    private String pk;
    private String name;
    private String company;
    private int experience;
    private int ctc;

    public Employee() {
    }

    public Employee(String pk, String name, String company, int experience, int ctc) {
        this.pk = pk;
        this.name = name;
        this.company = company;
        this.experience = experience;
        this.ctc = ctc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getCtc() {
        return ctc;
    }

    public void setCtc(int ctc) {
        this.ctc = ctc;
    }


    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return experience == employee.experience && ctc == employee.ctc && Objects.equals(pk, employee.pk) && Objects.equals(name, employee.name) && Objects.equals(company, employee.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, name, company, experience, ctc);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "pk='" + pk + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", experience=" + experience +
                ", ctc=" + ctc +
                '}';
    }
}
