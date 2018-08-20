package com.citytuike.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.constant.Constant;
import com.citytuike.model.TpFreightConfig;
import com.citytuike.model.TpGoods;
import com.citytuike.model.TpUsers;
import com.citytuike.service.TpGoodsService;
import com.citytuike.service.TpOrderService;
import com.citytuike.service.TpUsersService;

@Controller
@RequestMapping("api/Cart")
public class CartController {
	
	@Autowired
	private TpUsersService tpUsersService;
	@Autowired
	private TpGoodsService tpGoodsService;
	@Autowired
	private TpOrderService tpOrderService;
	/**
	 * @param model
	 * @param id
	 * @param p
	 * @return
	 * 商品列表
	 */
	@RequestMapping(value="/ajaxGoodsList",method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String ajaxGoodsList(Model model,@RequestParam(required=true) String token,
			@RequestParam(required=true) String address_id,
			@RequestParam(required=false) String invoice_title,
			@RequestParam(required=false) String taxpayer,
			@RequestParam(required=false) String coupon_id,
			@RequestParam(required=false) String pay_points,
			@RequestParam(required=false) String user_money,
			@RequestParam(required=false) String user_note,
			@RequestParam(required=true) String goods_id,
			@RequestParam(required=true) String goods_num,
			@RequestParam(required=false) String item_id,
			@RequestParam(required=false) String action,
			@RequestParam(required=false) String payPwd,
			@RequestParam(required=false) String act,
			@RequestParam(required=false) String invoice_email,
			@RequestParam(required=false) String invoice_contact,
			@RequestParam(required=false) String is_invoice,
			@RequestParam(required=false) String shipping_id,
			@RequestParam(required=false) String type){
		JSONObject jsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObj.put("status", "0");
		jsonObj.put("msg", "失败!");
		TpUsers tpUsers = tpUsersService.findOneByToken(token);
		if (null == tpUsers) {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "请先登陆!");
			return jsonObj.toString();
		}
		TpGoods tpGoods = tpGoodsService.findById(Integer.parseInt(goods_id));
		double freightTotal = 0.00;
		double invoiceTotal = 0.00;
		if (null != tpGoods) {
			if (null != tpGoods.getIs_free_shipping() && tpGoods.getIs_free_shipping() == 0) {
				if (null != tpGoods.getTemplate_id() && tpGoods.getTemplate_id() > 0) {
					TpFreightConfig tpFreightConfig = tpOrderService.findByTemId(tpGoods.getTemplate_id());
					if (null != tpFreightConfig) {
						//运费模板
						freightTotal =(int)Math.rint(tpFreightConfig.getFirst_unit() / Integer.parseInt(goods_num)) 
								* tpFreightConfig.getFirst_money();
					}
				}
			}
			if (null != is_invoice && "1".equals(is_invoice)) {
				//发票运费
				invoiceTotal = tpGoods.getShop_price() * Integer.parseInt(goods_num) * Constant.INVOICE_FREIGHT;
			}
			
		}
		jsonObj.put("return", data);
		jsonObj.put("status", "1");
		jsonObj.put("msg", "OK!");
		return jsonObj.toString();
	}
	
}
