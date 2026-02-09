package com.sime.gui.render;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.controls.CallbackMappings;
import com.sime.viewer.SimSnapshot;

public record FrameContext(Camera2D camera, double now, double frameDt,
                        SimSnapshot snapshot, CallbackMappings input) {


}
