package to.us.resume_builder.data.resume_components;

public class ResumeComponent {
    /**
     * The ID for this component.
     */
    protected final String id;

    /**
     * The visibility for the instance.
     */
    protected boolean visible = true;

    /**
     * sets the resume id for the instance.
     * 
     * @param id The new ID for this instance.
     */
    public ResumeComponent(String id) {
        this.id = id;
    }

    /**
     * Facilitates cloning of resume components
     * 
     * @param resumeComponent The component to clone
     */
    public ResumeComponent(ResumeComponent resumeComponent) {
        this.id = resumeComponent.id;
        this.visible = resumeComponent.visible;
    }

    /**
     * returns the ID for this instance.
     * 
     * @return id The ID for this instance.
     */
    public String getID() {
        return id;
    }

    /**
     * Get the current visibility for this instance.
     * 
     * @return visible The current visibility of the item.
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the object.
     * 
     * @param visible The visibility to set to.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public ResumeComponent clone() {
        return new ResumeComponent(this);
    }
}
