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

	private int totalPageNumber = 0; // 總頁數
	private int pageSize = 10; // 每頁資料筆數
	private int currentPage = 1; // 當前頁數

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
			// -----客戶註冊-----------
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			String birthday = request.getParameter("birthday");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");

			// Server驗證
			List<String> errors = new ArrayList<>();
			if (userid == null || userid.equals("")) {
				errors.add("客戶帳號不能為空");
			}
			if (name == null || name.equals("")) {
				errors.add("客戶姓名不能為空");
			}
			if (password == null || password2 == null || !password.equals(password2)) {
				errors.add("兩次輸入密碼不相同");
			}
			String pattern = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
			if (!Pattern.matches(pattern, birthday)) {
				errors.add("出生日期格式有誤");
			}

			if (errors.size() > 0) { // 驗證失敗
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("customer_reg.jsp").forward(request, response);
			} else { // 驗證成功
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
					errors.add("User ID 已註冊過。");
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
				errors.add("輸入的帳號ID或密碼有誤");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		} else if ("list".equals(action)) {
			// ------------商品列表------------------------
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
			// ------------商品列表分頁------------------------
			String page = request.getParameter("page");
			if (page.equals("prev")) { // 上一頁
				if (currentPage > 1) {
					currentPage--;
				}
			} else if (page.equals("next")) { // 下一頁
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
			// 添加購物車
			Long goodsid = new Long(request.getParameter("id"));
			String goodsname = request.getParameter("name");
			Float price = new Float(request.getParameter("price"));

			// 購物車結構LIST，LIST元素為MAP，每個MPA=一個商品
			// 從Session中取出購物車
			List<Map<String, Object>> cart = (List<Map<String, Object>>) request.getSession().getAttribute("cart");
			if (cart == null) {
				cart = new ArrayList<Map<String, Object>>();
				request.getSession().setAttribute("cart", cart);
			}

			// 購物車中有此商品
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

			// 購物車內無此商品
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
			// 查看購物車
			// 從session中取出購物車
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
			// 提交訂單
			// 從session中取出購物車
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
			// 提交訂單
			String ordersid = ordersService.submitOrders(cart);
			request.setAttribute("ordersid", ordersid);
			request.getRequestDispatcher("order_finish.jsp").forward(request, response);
			// 清空購物車
			request.getSession().removeAttribute("cart");
		} else if ("main".equals(action)) {
			request.getRequestDispatcher("main.jsp").forward(request, response);

		} else if ("logout".equals(action)) {
			// 登出
			// 清空購物車
			request.getSession().removeAttribute("cart");
			// 清空登入訊息
			request.getSession().removeAttribute("customer");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else if ("reg_init".equals(action)) {
			//進入用戶註冊頁面
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
