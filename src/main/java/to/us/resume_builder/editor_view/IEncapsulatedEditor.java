package to.us.resume_builder.editor_view;

public interface IEncapsulatedEditor {
    /**
     * Saves the data in this EditPane to the copy of the ResumeData <em>in
     * RAM</em>.
     */
    void save();

    /**
     * Determines if the current Category has been modified
     * @return boolean indicating whether the Category was edited
     */
    boolean isModified();
}
