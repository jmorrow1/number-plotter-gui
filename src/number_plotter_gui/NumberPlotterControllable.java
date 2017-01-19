package number_plotter_gui;

import curves.Curve;
import draw_modes.DrawMode;
import int_properties.IntProperty;
import int_properties.IntSequence;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public interface NumberPlotterControllable {
    public final static String sequence = IntSequence.class.getSimpleName();
    public final static String curve = Curve.class.getSimpleName();
    public final static String property = IntProperty.class.getSimpleName();
    public final static String drawMode = DrawMode.class.getSimpleName();
    
    public void setSequence(String valueName);
    public void setCurve(String valueName);
    public void setProperty(String valueName);
    public void setDrawMode(String valueName);
    public default void setVariable(String variableName, String valueName) {
        if (variableName.equals(sequence)) {
            setSequence(valueName);
        }
        else if (variableName.equals(curve)) {
            setCurve(valueName);
        }
        else if (variableName.equals(property)) {
            setProperty(valueName);
        }
        else if (variableName.equals(drawMode)) {
            setDrawMode(valueName);
        }
        else {
            System.err.println("setVariable called with unknown variable name: " + variableName + ".");
        }
    }
}