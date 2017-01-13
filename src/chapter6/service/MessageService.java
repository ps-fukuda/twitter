package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;

public class MessageService {

	private int userId;
	private String account;

	public MessageService() {

	}

	public MessageService(int id) {
		this.userId = id;
	}

	public MessageService(String account) {
		this.account = account;
	}

	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

			commit(connection);
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

	private static final int LIMIT_NUM = 1000;

	public List<UserMessage> getMessage() {

		Connection connection = null;
		List<UserMessage> ret;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			if (userId != 0) {
				ret = messageDao.getSpecificUserMessages(connection, userId, LIMIT_NUM);
			} else if (account != null) {
				ret = messageDao.getSpecificAccountMessages(connection, account, LIMIT_NUM);
			} else {
				ret = messageDao.getUserMessages(connection, LIMIT_NUM);
			}

			commit(connection);

			return ret;
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
