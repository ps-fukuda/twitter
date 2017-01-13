package chapter6.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserMessage;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<UserMessage> messages;
		String userId = request.getParameter("user_id");
		String account = request.getParameter("account");
		if (userId != null) {
			int id = 0;
			try {
				id = Integer.parseInt(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			messages = new MessageService(id).getMessage();
		} else if (account != null) {
			messages = new MessageService(account).getMessage();
		} else {
			messages = new MessageService().getMessage();
		}

		User user = (User) request.getSession().getAttribute("loginUser");
		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}



		request.setAttribute("messages", messages);
		request.setAttribute("isShowMessageForm", isShowMessageForm);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
