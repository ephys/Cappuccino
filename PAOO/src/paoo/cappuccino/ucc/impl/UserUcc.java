package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * Legit implementation of the User use case controller.
 *
 * @author Guylian Cox
 */
class UserUcc implements IUserUcc {

	@Inject
	private IEntityFactory entityFactory;
	@Inject
	private IDalService dalService;
	@Inject
	private IUserDao userDao;
	@Inject
	private IStringHasher hasher;

	@Override
	public IUserDto register(String username, char[] password,
			String firstName, String lastName, String email) {
		ValidationUtil.ensureFilled(username, "username");
		username = username.trim();

		if (username.matches("^.*\\s.*$")) {
			throw new IllegalArgumentException(
					"username cannot contain any spaces");
		}

		ValidationUtil.validatePassword(password, "password");
		ValidationUtil.ensureFilled(firstName, "firstName");
		ValidationUtil.ensureFilled(lastName, "lastName");

		ValidationUtil.ensureNotNull(email, "email");
		email = email.trim();
		if (!StringUtils.isEmail(email)) {
			throw new IllegalArgumentException("Invalid email format");
		}

		firstName = firstName.trim();
		lastName = lastName.trim();
		IUser registeredUser = entityFactory.createUser(username,
				hasher.hash(password), lastName, firstName, email,
				IUserDto.Role.USER);

		registeredUser = userDao.createUser(registeredUser);

		return registeredUser;
	}

	@Override
	public IUserDto logIn(String username, char[] password) {
		ValidationUtil.ensureFilled(username, "username");
		ValidationUtil.ensureFilled(password, "password");

		IUser user = userDao.fetchUserByUsername(username.trim());
		if (user == null) {
			return null;
		}

		IHashHolderDto realPassword = user.getPassword();
		if (!hasher.matchHash(password, realPassword)) {
			return null;
		}

		// update the hash algorithm used to store the password if we changed
		// it.
		if (hasher.isHashOutdated(realPassword)) {
			user.setPassword(hasher.hash(password));
			userDao.updateUser(user);
		}

		return user;
	}
}
