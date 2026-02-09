package com.sime.gui;

import java.util.List;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.controls.CallbackMappings;
import com.sime.gui.render.Renderer;
import com.sime.viewer.SimDriver;

public class GuiDriverBuilder {
    
    private long window;
    private Camera2D camera;
    private SimDriver simDriver;
    private CallbackMappings mappings;
    private List<Renderer> renderers;


    public GuiDriver build() {
        return new GuiDriver(window, camera, simDriver, mappings, renderers);
    }

    public GuiDriverBuilder withWindow(long window) {
        this.window = window;
        return this;
    }

    public GuiDriverBuilder withCamera(Camera2D camera) {
        this.camera = camera;
        return this;
    }

    public GuiDriverBuilder withCallbackMappings(CallbackMappings mappings) {
        this.mappings = mappings;
        return this;
    }

    public GuiDriverBuilder withSimDriver(SimDriver simDriver) {
        this.simDriver = simDriver;
        return this;
    }

    public GuiDriverBuilder withRenderers(List<Renderer> renderers) {
        this.renderers = renderers;
        return this;
    }


}
