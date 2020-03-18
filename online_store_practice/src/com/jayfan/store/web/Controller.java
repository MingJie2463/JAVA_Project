package com.jayfan.store.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jayfan.store.domain.Customer;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.service.CustomerService;
import com.jayfan.store.service.GoodsService;
import com.jayfan.store.service.OrdersService;
import com.jayfan.store.service.ServiceException;
import com.jayfan.store.service.imp.CustomerServiceImp;
import com.jayfan.store.service.imp.GoodsServiceImp;
import com.jayfan.store.service.imp.OrdersServiceImp;

/**
 * Servlet implementation class Controller
 */

//@WebServlet("/Controller") 
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private CustomerService customerService = new CustomerServiceImp();
	private GoodsService goodsService = new GoodsServiceImp();
	private OrdersService ordersService = new OrdersServiceImp();

	private int totalPageNumber = 0; // �`����
	private int pageSize = 10; // �C����Ƶ���
	private int currentPage = 1; // ��e����

	/*
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		pageSize = new Integer(config.getInitParameter("pageSize"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");
		if ("reg".equals(action)) {
			// -----�Ȥ���U-----------
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			String birthday = request.getParameter("birthday");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");

			// Server����
			List<String> errors = new ArrayList<>();
			if (userid == null || userid.equals("")) {
				errors.add("�Ȥ�b�����ର��");
			}
			if (name == null || name.equals("")) {
				errors.add("�Ȥ�m�W���ର��");
			}
			if (password == null || password2 == null || !password.equals(password2)) {
				errors.add("�⦸��J�K�X���ۦP");
			}
			String pattern = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
			if (!Pattern.matches(pattern, birthday)) {
				errors.add("�X�ͤ���榡���~");
			}

			if (errors.size() > 0) { // ���ҥ���
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
			} else { // ���Ҧ��\
				Customer customer = new Customer();
				customer.setId(userid);
				customer.setName(name);
				customer.setPassword(password);
				try {
					Date d;
					d = dateFormat.parse(birthday);
					customer.setBirthday(d);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customer.setAddress(address);
				customer.setPhone(phone);

				try {
					customerService.register(customer);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				} catch (ServiceException e) {
					errors.add("User ID �w���U�L�C");
					request.setAttribute("errors", errors);
					request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
				}

			}

		} else if ("login".equals(action)) {
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			Customer customer = new Customer();
			customer.setId(userid);
			customer.setPassword(password);
			if (customerService.login(customer)) {// login success
				HttpSession session = request.getSession();
				session.setAttribute("customer", customer);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			} else {
				List<String> errors = new ArrayList<>();
				errors.add("��J���b��ID�αK�X���~");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		} else if ("list".equals(action)) {
			// ------------�ӫ~�C��------------------------
			List<Goods> goodsList = goodsService.queryAll();

			if (goodsList.size() % pageSize == 0) {
				totalPageNumber = goodsList.size() / pageSize;
			} else {
				totalPageNumber = (goodsList.size() / pageSize) + 1;
			}

			request.setAttribute("totalPageNumber", totalPageNumber);
			request.setAttribute("currentPage", currentPage);

			int start = (currentPage - 1) * pageSize;
			int end = currentPage * pageSize;
			if (currentPage == totalPageNumber) {
				end = goodsList.size();
			}

			request.setAttribute("goodsList", goodsList.subList(start, end));
			// request.setAttribute("goodsList", goodsList);

			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		} else if ("paging".equals(action)) {
			// ------------�ӫ~�C�����------------------------
			String page = request.getParameter("page");
			if (page.equals("prev")) { // �W�@��
				if (currentPage > 1) {
					currentPage--;
				}
			} else if (page.equals("next")) { // �U�@��
				if (currentPage < totalPageNumber) {
					currentPage++;
				}
			} else {
				currentPage = Integer.valueOf(page);
			}
			int start = (currentPage - 1) * pageSize;
			int end = currentPage * pageSize;

			List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

			request.setAttribute("totalPageNumber", totalPageNumber);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("goodsList", goodsList);
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		} else if ("detail".equals(action)) {
			String goodsid = request.getParameter("id");
			Goods goods = goodsService.queryDetail(new Long(goodsid));

			request.setAttribute("goods", goods);
			request.getRequestDispatcher("goods_detail.jsp").forward(request, response);

		} else if ("add".equals(action)) {
			// �K�[�ʪ���
			Long goodsid = new Long(request.getParameter("id"));
			String goodsname = request.getParameter("name");
			Float price = new Float(request.getParameter("price"));

			// �ʪ������cLIST�ALIST������MAP�A�C��MPA=�@�Ӱӫ~
			// �qSession�����X�ʪ���
			List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
			if (cart == null) {
				cart = new ArrayList<Map<String, Object>>();
				request.getSession().setAttribute("cart", cart);
			}

			// �ʪ����������ӫ~
			int addedBeforeFlag = 0;
			for (Map<String, Object> item : cart) {
				Long goodsid2 = (Long) item.get("goodsid");
				if (goodsid.equals(goodsid2)) {
					Integer quantity = (Integer) item.get("quantity");
					quantity++;
					item.put("quantity", quantity);
					addedBeforeFlag++;
				}
			}

			// �ʪ������L���ӫ~
			if (addedBeforeFlag == 0) {
				Map<String, Object> item = new HashMap<>();
				item.put("goodsid", goodsid);
				item.put("goodsname", goodsname);
				item.put("quantity", 1);
				item.put("price", price);
				cart.add(item);
			}

			System.out.println(cart);

			String pagename = request.getParameter("pagename");

			if (pagename.equals("list")) {
				int start = (currentPage - 1) * pageSize;
				int end = currentPage * pageSize;

				List<Goods> goodsList = goodsService.queryByStartEnd(start, end);

				request.setAttribute("totalPageNumber", totalPageNumber);
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("goodsList", goodsList);
				request.getRequestDispatcher("goods_list.jsp").forward(request, response);
			} else if (pagename.equals("detail")) {
				Goods goods = goodsService.queryDetail(new Long(goodsid));
				request.setAttribute("goods", goods);
				request.getRequestDispatcher("goods_detail.jsp").forward(request, response);
			}

		} else if ("cart".equals(action)) {
			// �d���ʪ���
			// �qsession�����X�ʪ���
			List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
			double total = 0.0;

			if (cart != null) {
				for (Map<String, Object> item : cart) {
					Long goodsid2 = (Long) item.get("goodsid");
					Integer quantity = (Integer) item.get("quantity");
					Float price = (Float) item.get("price");
					double subtotal = price * quantity;
					total += subtotal;
				}
			}
			request.setAttribute("total", total);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		} else if ("sub_ord".equals(action)) {
			// ����q��
			// �qsession�����X�ʪ���
			List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
			for (Map<String, Object> item : cart) {
				Long goodsid = (Long) item.get("goodsid");
				String strquantity = request.getParameter("quantity_" + goodsid);
				int quantity = 0;
				try {
					quantity = new Integer(strquantity);
				} catch (Exception e) {

				}
				item.put("quantity", quantity);
			}
			// ����q��
			String ordersid = ordersService.submitOrders(cart);
			request.setAttribute("ordersid", ordersid);
			request.getRequestDispatcher("order_finish.jsp").forward(request, response);
			// �M���ʪ���
			request.getSession().removeAttribute("cart");
		} else if ("main".equals(action)) {
			request.getRequestDispatcher("main.jsp").forward(request, response);

		} else if ("logout".equals(action)) {
			// �n�X
			// �M���ʪ���
			request.getSession().removeAttribute("cart");
			// �M�ŵn�J�T��
			request.getSession().removeAttribute("customer");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if ("reg_init".equals(action)) {
			//�i�J�Τ���U����
			request.getRequestDispatcher("customer_reg.jsp").forward(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
