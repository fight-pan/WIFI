package com.quark.wificontrol.alipay;

public class CommentAliPay {
	String out_trade_no; // out_trade_no String
	
	String account; // 支付宝账号 int
	String partner; // partner int
	String private_key; // private_key int
	String private_key_pkcs8; // private_key_pkcs8 int
	
	String status; // 1-操作成功 int
	String code; //

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPrivate_key() {
		return private_key;
	}

	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}

	public String getPrivate_key_pkcs8() {
		return private_key_pkcs8;
	}

	public void setPrivate_key_pkcs8(String private_key_pkcs8) {
		this.private_key_pkcs8 = private_key_pkcs8;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
