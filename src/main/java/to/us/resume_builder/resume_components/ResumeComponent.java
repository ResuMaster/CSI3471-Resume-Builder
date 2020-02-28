package to.us.resume_builder.resume_components;

public class ResumeComponent {
    /**
     * The ID for this component.
     */
    protected final String id;
    protected boolean visible = true;

    public ResumeComponent(String id) {
        this.id = id;
    }

    /**
     * returns the ID for this instance
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the current visibility for this instance
     * @return visible
     */
    public boolean getVisible(){
        return visible;
    }

    /**
     * Toggles the visibility of the object.
     */
    void toggleVisibility(){
        visible = !visible;
    }

    /**
     * Sets the visibility of the object
     * @param visible the visibility the object is set to
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
