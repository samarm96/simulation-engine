package com.sime.model;

public class MultiBodyAstroModel implements AstroModel {

    private ForceModel fm;

    public MultiBodyAstroModel() {
        this.fm = new MultiBodyForceModel();
    }

    @Override
    public ForceModel forceModel() {
        return this.fm;
    }
}
