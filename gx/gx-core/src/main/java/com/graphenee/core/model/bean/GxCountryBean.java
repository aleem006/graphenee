package com.graphenee.core.model.bean;

import java.io.Serializable;
import java.util.Collection;

import com.graphenee.core.model.BeanCollectionFault;

public class GxCountryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer oid;
	private Integer numericCode;
	private Boolean isActive = true;
	private String alpha3Code;
	private String countryName;

	private BeanCollectionFault<GxStateBean> stateBeanColltionFault = BeanCollectionFault.emptyCollectionFault();

	private BeanCollectionFault<GxCityBean> cityBeanColltionFault = BeanCollectionFault.emptyCollectionFault();

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(Integer numericCode) {
		this.numericCode = numericCode;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BeanCollectionFault<GxStateBean> getStateBeanColltionFault() {
		return stateBeanColltionFault;
	}

	public void setStateBeanColltionFault(BeanCollectionFault<GxStateBean> stateBeanColltionFault) {
		this.stateBeanColltionFault = stateBeanColltionFault;
	}

	public BeanCollectionFault<GxCityBean> getCityBeanColltionFault() {
		return cityBeanColltionFault;
	}

	public void setCityBeanColltionFault(BeanCollectionFault<GxCityBean> cityBeanColltionFault) {
		this.cityBeanColltionFault = cityBeanColltionFault;
	}

	public void setCityBeans(Collection<GxCityBean> cityBeans) {
		setCityBeanColltionFault(BeanCollectionFault.collectionFault(cityBeans));
		getCityBeanColltionFault().markAsModified();
	}

	public Collection<GxCityBean> getCityBeans() {
		return getCityBeanColltionFault().getBeans();
	}

	public void setStateBeans(Collection<GxStateBean> stateBeans) {
		setStateBeanColltionFault(BeanCollectionFault.collectionFault(stateBeans));
		getStateBeanColltionFault().markAsModified();
	}

	public Collection<GxStateBean> getStateBeans() {
		return getStateBeanColltionFault().getBeans();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GxCountryBean other = (GxCountryBean) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}
}