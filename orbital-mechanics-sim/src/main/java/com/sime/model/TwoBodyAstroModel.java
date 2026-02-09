package com.sime.model;

public class TwoBodyAstroModel implements AstroModel {

    private ForceModel fm;

    public TwoBodyAstroModel() {
        this.fm = new TwoBodyForceModel();
    }

    @Override
    public ForceModel forceModel() {
        return this.fm;
    }

}
