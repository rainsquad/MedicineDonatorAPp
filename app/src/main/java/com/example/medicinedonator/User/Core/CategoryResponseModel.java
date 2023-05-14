package com.example.medicinedonator.User.Core;

import java.util.List;

public class CategoryResponseModel {

    List<Medicine> ENT;

    List<Medicine> Heart;

    List<Medicine> CNS;

    List<Medicine> Muscles;

    public List<Medicine> getENT() {
        return ENT;
    }

    public void setENT(List<Medicine> ENT) {
        this.ENT = ENT;
    }

    public List<Medicine> getHeart() {
        return Heart;
    }

    public void setHeart(List<Medicine> heart) {
        Heart = heart;
    }

    public List<Medicine> getCNS() {
        return CNS;
    }

    public void setCNS(List<Medicine> CNS) {
        this.CNS = CNS;
    }

    public List<Medicine> getMuscles() {
        return Muscles;
    }

    public void setMuscles(List<Medicine> muscles) {
        Muscles = muscles;
    }
}
