package to.us.resume_builder.presentation;

/**
 * This interface provides common {@link #save() save} and {@link #isModified()
 * isModified} methods for editors.
 *
 * @author Matthew McCaskill
 */
public interface IEncapsulatedEditor {
    /**
     * Saves the data in this EditPane to the copy of the ResumeData <em>in
     * RAM</em>.
     */
    void save();

    /**
     * Determines if the current Category has been modified
     *
     * @return boolean indicating whether the Category was edited
     */
    boolean isModified();
}
