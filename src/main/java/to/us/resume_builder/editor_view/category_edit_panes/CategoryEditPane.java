package to.us.resume_builder.editor_view.category_edit_panes;

import to.us.resume_builder.editor_view.IEncapsulatedEditor;
import to.us.resume_builder.resume_components.category.Category;

import javax.swing.*;

/**
 * Standard interface between the main Resume Editor panel and the editor
 * subcomponents (e.g., editor for a bulleted list, the header, etc.).
 * 
 * @author Micah
 */
public abstract class CategoryEditPane extends JPanel implements IEncapsulatedEditor {

	/**
	 * Version on 2/21.
	 */
	private static final long serialVersionUID = 3001154674271049780L;
}
