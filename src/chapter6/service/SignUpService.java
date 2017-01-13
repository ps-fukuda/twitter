package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;

import chapter6.dao.UserDao;

public class SignUpService {

	public boolean validate(String account) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			if (userDao.signupDuplicateCheck(connection, account)) {
				return true;
			}
			commit(connection);

			return false;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
