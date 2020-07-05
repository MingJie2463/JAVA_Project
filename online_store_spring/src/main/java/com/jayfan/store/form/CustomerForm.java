package com.jayfan.store.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerForm {
 
	@NotBlank(message = "User ID不可空白")
	private String id;
	
	@NotBlank(message = "User名稱不可空白")
	private String name;		

	@NotBlank(message = "密碼不可空白")
	@Size(min=6)
	private String password;	

	private String confirmPassword;
	
	private String address;

	private String phone;

	private long birthday;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}
	
	public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public boolean confirmPassword() {
        if (this.password.equals(this.confirmPassword)) {
            return true;
        }
        return false;
    }

}
