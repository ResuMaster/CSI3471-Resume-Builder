package to.us.resume_builder;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * This class offers access to basic dialogs without boilerplate (yes/no/cancel,
 * 
 * @author Micah
 */
public final class AlertUtilities {
	public static final String APP_NAME = "Name To Be Determined Resume Builder";

	/**
	 * Display an error message.
	 * 
	 * @param parent  The component that suffered the error
	 * @param message The message to show
	 */
	public static final void showError(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, APP_NAME, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Get a yes/no/cancel input.
	 * 
	 * @param parent  The component requesting input
	 * @param message The prompt
	 * @return true/false if yes/no, null if cancelled.
	 */
	public static final Boolean showYesNoCancel(Component parent, String message) {
		Boolean result;
		int input = JOptionPane.showConfirmDialog(parent, message, APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION);

		switch (input) {
		case JOptionPane.YES_OPTION:
			result = true;
			break;

		case JOptionPane.NO_OPTION:
			result = false;
			break;

		default:
			result = null;
			break;
		}

		return result;
	}

	private AlertUtilities() {
		// Do nothing
	}
}
