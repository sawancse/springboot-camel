package com.emart.user.profile.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.CreationTimestamp;

import com.emart.user.profile.util.DateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "User", catalog = "", schema = "emart")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT x FROM User x"),
		@NamedQuery(name = "User.findByUserId", query = "SELECT x FROM User x WHERE x.userId = :userId"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT x FROM User x WHERE x.email = :email")
,@NamedQuery(name = "User.updateEmail", query = "update User x set x.email = : newEmail where x.email = :email"),})
@XmlRootElement(name = "User")
@XmlType(propOrder = { "userId", "name", "email", "mobile","password","status","userType","expiry","permanentAddress","permanentPinCode","deliveryAddress","deliveryPinCode","userIdProofType","country","state","city","vendorShopPinCode","creationDate", "lastModificationDate" })
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1051349223537895941L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "UserId", nullable = false, unique = true)
	Long userId;

	@Basic(optional = false)
	@Column(name = "Name", nullable = false, length = 50)
	String name;

	@Basic(optional = false)
	@Column(name = "Email", nullable = false, length = 50,unique = true)
	String email;

	@Basic(optional = false)
	@Column(name = "Mobile", nullable = false, length = 12,unique = true)
	String mobile;
	
	@Basic(optional = false)
	@Column(name = "Password", nullable = false, length = 50)
	String password;

	@Column(name = "STATUS", length = 20)
	String status;
	
	@Column(name = "UserType", length = 20)
	String userType;
	
	
	//expiry
	@Column(name = "expiry", length = 20)
	String expiry;

	@Column(name = "PermanentAddress", length = 20)
	String permanentAddress;
	
	@Column(name = "PermanentPinCode", length = 20)
	String permanentPinCode;
	
	@Column(name = "DeliveryAddress", length = 20)
	String deliveryAddress;
	
	@Column(name = "DeliveryPinCode", length = 20)
	String deliveryPinCode;
	
	@Column(name = "UserIdProofType", length = 20)
	String userIdProofType;
	
	@Column(name = "Country", length = 20)
	String country;
	
	@Column(name = "State", length = 20)
	String state;
	
	@Column(name = "City", length = 20)
	String city;
	
	@Column(name = "VendorShopPinCode", length = 20)
	String vendorShopPinCode;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creationDate",nullable = false)
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	Date creationDate;

	@Column(name = "lastModificationDate", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	// @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	Date lastModificationDate;
	
	
	public User(Long userId,String name, String email, String password, Date creationDate, Date lastModificationDate, String mobile, String userType, String expiry, String permanentAddress, String permanentPinCode, String deliveryAddress, String deliveryPinCode, String userIdProofType, String country, String state, String city, String vendorShopPinCode) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.creationDate = creationDate;
		this.userType=userType;
		this.expiry=expiry;
		this.permanentAddress=permanentAddress;
		this.permanentPinCode=permanentPinCode;
		this.deliveryAddress=deliveryAddress;
		this.deliveryPinCode=deliveryPinCode;
		this.userIdProofType=userIdProofType;
		this.country=country;
		this.state=state;
		this.city=city;
		this.vendorShopPinCode=vendorShopPinCode;
		 	
		this.lastModificationDate = lastModificationDate;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	  public String getPassword() { return password; }
	 
	
	  public void setPassword(String password) { this.password = password; }
	 
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@XmlElement(name = "lastModificationDate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPermanentPinCode() {
		return permanentPinCode;
	}

	public void setPermanentPinCode(String permanentPinCode) {
		this.permanentPinCode = permanentPinCode;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPinCode() {
		return deliveryPinCode;
	}

	public void setDeliveryPinCode(String deliveryPinCode) {
		this.deliveryPinCode = deliveryPinCode;
	}

	public String getUserIdProofType() {
		return userIdProofType;
	}

	public void setUserIdProofType(String userIdProofType) {
		this.userIdProofType = userIdProofType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVendorShopPinCode() {
		return vendorShopPinCode;
	}

	public void setVendorShopPinCode(String vendorShopPinCode) {
		this.vendorShopPinCode = vendorShopPinCode;
	}

	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userId != null ? userId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", mob="+ mobile + "]";
	}
	
}

