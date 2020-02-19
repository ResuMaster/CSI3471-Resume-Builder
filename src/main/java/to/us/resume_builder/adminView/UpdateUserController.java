package to.us.resume_builder.adminView;

import to.us.resume_builder.user.User;
import to.us.resume_builder.user.UserDBC;
import to.us.resume_builder.user.UserRole;

public class UpdateUserController {
	private User currentAdmin;
	private User toChange = null;

	/**
	 * Factory method to create a controller to modify a user.
	 * 
	 * @param requester The {@link User} requesting the controller
	 * @return A controller, if requester was an Admin; null otherwise.
	 */
	public static UpdateUserController updateUserControllerFactory(User requester) {
		if (requester.getRole() == UserRole.ADMIN)
			return new UpdateUserController(requester);
		return null;
	}

	/**
	 * Ensure that the role of this user can be changed; if so, begin to change its
	 * role.
	 * 
	 * @param user The user to change the role of.
	 * @return Whether or not this user's role can be changed.
	 */
	public boolean initiateRoleChange(User user) {
		boolean changeValid = false;

		if (!user.equals(currentAdmin)) {
			toChange = user;
			changeValid = true;
		}

		return changeValid;
	}

	/**
	 * Updates the role of the current user to newRole.
	 * 
	 * @param newRole The role for the current user to assume.
	 */
	public void changeUserRole(UserRole newRole) {
		if (toChange != null && !toChange.equals(currentAdmin)) {
			UserDBC connector = new UserDBC(toChange);
			connector.changeRole(newRole);
		}
	}

	private UpdateUserController(User admin) {
		currentAdmin = admin;
	}
}
