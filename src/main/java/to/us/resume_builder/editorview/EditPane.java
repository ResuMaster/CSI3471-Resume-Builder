package to.us.resume_builder.editorview;

import javax.swing.JPanel;

/**
 * Standard interface between the main Resume Editor panel and the editor
 * subcomponents (e.g., editor for a bulleted list, the header, etc.).
 * 
 * @author Micah
 */
public abstract class EditPane extends JPanel {

	/**
	 * Version on 2/21.
	 */
	private static final long serialVersionUID = 3001154674271049780L;

	/**
	 * Saves the data in this EditPane to the copy of the ResumeData <em>in
	 * ram</em>.
	 */
	abstract void save();
}
