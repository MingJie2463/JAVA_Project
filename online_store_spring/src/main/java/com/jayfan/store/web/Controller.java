package com.jayfan.store.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jayfan.store.domain.CustomerRepository;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.form.CustomerForm;
import com.jayfan.store.service.CustomerService;
import com.jayfan.store.service.GoodsService;
import com.jayfan.store.service.OrdersService;
import com.jayfan.store.service.imp.CustomerServiceImp;
import com.jayfan.store.service.imp.GoodsServiceImp;
import com.jayfan.store.service.imp.OrdersServiceImp;
import com.jayfan.store.domain.Customer;

@org.springframework.stereotype.Controller
public class Controller {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CustomerServiceImp customerService;

	@Autowired
	private GoodsServiceImp goodsService;

	@Autowired
	private OrdersServiceImp ordersService;

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/main")
	public String mainPage() {
		return "main";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(@RequestParam(name = "userid") String username,
			@RequestParam(name = "password") String password, HttpSession session, Map<String, Object> map) {
		Customer customer = new Customer();
		customer.setId(username);
		customer.setPassword(password);
		if (customerService.login(customer)) {
			session.setAttribute("customer", customer);
			return "redirect:/main";
		} else {
			map.put("errors", "輸入的帳號ID或密碼有誤");
			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("cart");
		session.removeAttribute("customer");
		return "login";
	}

	@GetMapping("/reg")
	public String registerPage() {
		// model.addAttribute("customerForm", new CustomerForm());
		return "customer_reg";
	}

	@PostMapping("/reg")
	public String registerPost(@Valid CustomerForm customerForm, BindingResult result, Map<String, Object> map) {
		if (!customerForm.confirmPassword()) {
			result.rejectValue("confirmPassword", "confirmError", "兩次輸入密碼不一致");
		}
		if (result.hasErrors()) {
			ArrayList<String> errerList = new ArrayList<String>();
			for (ObjectError e : result.getAllErrors()) {
				errerList.add(e.getDefaultMessage());
			}
			map.put("errors", errerList);
			return "customer_reg";
		} else {
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerForm, customer);
			customerRepository.save(customer);
			return "redirect:/login";
		}

	}

	private int pageSize = 10;
	private int currentPage = 0;
	private int totalPageNumber = 0;

	@GetMapping("/list")
	public String listPage(Map<String, Object> map) {

		List<Goods> goodsList = goodsService.queryAll();
		totalPageNumber = (int) Math.ceil(goodsList.size() / (float) pageSize);
		currentPage = 1;

		map.put("totalPageNumber", totalPageNumber);
		map.put("currentPage", currentPage);
		map.put("goodsList", goodsList.subList(currentPage - 1, currentPage * pageSize));

		return "goods_list";
	}

	@GetMapping("/paging")
	public String getPage(@RequestParam(name = "page", required = true) String page, Map<String, Object> map) {

		if (totalPageNumber <= 0) {
			return "redirect:/list";
		} else {
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
			map.put("totalPageNumber", totalPageNumber);
			map.put("currentPage", currentPage);
			List<Goods> goodsList = goodsService.queryByStartOffset(currentPage, pageSize);
			map.put("goodsList", goodsList);
			return "goods_list";
		}

	}

	@GetMapping("/detail")
	public String getDetail(@RequestParam(name = "id", required = true) String id, Map<String, Object> map) {
		Goods goods = goodsService.queryDetail(new Long(id));
		map.put("goods", goods);
		return "goods_detail";
	}

	@RequestMapping("/add")
	public String addCart(@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "price", required = true) String price,
			@RequestParam(name = "pagename", required = true) String pagename, Map<String, Object> map,
			HttpSession session) {

		List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<Map<String, Object>>();
			session.setAttribute("cart", cart);
		}

		// 購物車中有此商品
		int addedBeforeFlag = 0;
		Long goodsId = new Long(id);
		for (Map<String, Object> item : cart) {
			Long goodsid2 = Long.valueOf(item.get("goodsid").toString());
			if (goodsId.equals(goodsid2)) {
				Integer quantity = (Integer) item.get("quantity");
				quantity++;
				item.put("quantity", quantity);
				addedBeforeFlag++;
			}
		}

		// 購物車內無此商品
		if (addedBeforeFlag == 0) {
			Map<String, Object> item = new HashMap<>();
			item.put("goodsid", id);
			item.put("goodsname", name);
			item.put("quantity", 1);
			item.put("price", price);
			cart.add(item);
		}

		System.out.println(cart);

		if (pagename.equals("list")) {
			map.put("totalPageNumber", totalPageNumber);
			map.put("currentPage", currentPage);
			List<Goods> goodsList = goodsService.queryByStartOffset(currentPage, pageSize);
			map.put("goodsList", goodsList);
			return "goods_list";
		} else if (pagename.equals("detail")) {
			Goods goods = goodsService.queryDetail(new Long(id));
			map.put("goods", goods);
			return "goods_detail";
		} else {
			return "main";
		}

	}

	@GetMapping("/cart")
	public String cartPage(HttpSession session) {
		// 查看購物車
		// 從session中取出購物車
		List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
		double total = 0.0;

		if (cart != null) {
			for (Map<String, Object> item : cart) {
				Integer quantity = new Integer(item.get("quantity").toString());
				Float price = new Float(item.get("price").toString());
				double subtotal = price * quantity;
				total += subtotal;
			}
		}
		session.setAttribute("total", total);
		return "cart";
	}

	@PostMapping("/cart")
	public String cartPost(HttpServletRequest request, HttpSession session) {

		// 提交訂單
		// 從session中取出購物車
		List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
		for (Map<String, Object> item : cart) {
			Long goodsid = new Long( item.get("goodsid").toString());
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
		request.getSession().removeAttribute("cart");

		return "order_finish";
	}

	@GetMapping("/order_finish")
	public String orderSubmit() {
		return "order_finish";
	}

	@GetMapping("/exception")
	public String testException() {
		throw new RuntimeException("測試異常處理");
	}

}
