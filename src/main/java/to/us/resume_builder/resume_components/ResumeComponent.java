package to.us.resume_builder.resume_components;

public class ResumeComponent {
    /**
     * The ID for this component.
     */
    protected final String id;
    protected boolean visible;

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

    public boolean getVisible(){
        return visible;
    }

    void toggleVisibility(){
        visible = !visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
