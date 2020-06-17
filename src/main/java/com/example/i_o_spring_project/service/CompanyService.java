package com.example.i_o_spring_project.service;

import java.sql.SQLException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.exceptions.CouponsSystemExceptions;
import com.example.i_o_spring_project.exceptions.SystemExceptions;
import com.example.i_o_spring_project.model.Company;
import com.example.i_o_spring_project.model.Coupon;
import com.example.i_o_spring_project.repository.CompanyRepository;
import com.example.i_o_spring_project.repository.CouponRepository;

@Service
public class CompanyService {

	@Autowired
	private Company company;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;

	public CompanyService(ConfigurableApplicationContext applicationContext) {
		companyRepository = applicationContext.getBean(CompanyRepository.class);
		couponRepository = applicationContext.getBean(CouponRepository.class);
	}

	public boolean login(String email, String password) throws SQLException, CouponsSystemExceptions {
		Optional<Company> optionalCompany = companyRepository.findByEmail(email);
		if (optionalCompany.isEmpty()) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted email is incorrect");
		}
		Company company = optionalCompany.get();
		if (!company.getPassword().equals(password)) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED, "inserted password is incorrect");
		}
		return true;
	}
	@Transactional
	public void addACoupon(Coupon coupon) throws CouponsSystemExceptions {
		// object validation if.
		Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());

		if (optionalCoupon.isPresent()) {
			throw new CouponsSystemExceptions(SystemExceptions.VALUE_UNAVAILABLE,
					"this coupon already exists in the system.");
		}
		// if(optionalCoupon.get().getEndDate().before(when) {
		// the function .before works here, but the problem is to get an instance of NOW
		// to compare to it.
		// }
		if (optionalCoupon.get().getEndDate().before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's startDate cannot surpass its endDate ");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"The coupon's amount cannot be less than or equal to zero");
		}
		if (coupon.getPrice() <= 0) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"the coupon's price cannot be less than or equal to zero");

		}
		couponRepository.save(coupon);
		System.out.println("the Coupon has been added to the system");
	}
	
	@Transactional
	public void updateCoupon(Coupon coupon) throws SQLException, CouponsSystemExceptions, InterruptedException {
		// objectValidation.isTheObjectEmpty(coupon);
		// if (objectValidation.charactersHasExceeded(coupon)) {
		// throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
		// "characters have exceeded the attribute's given capacity");
		// }
		if (coupon.getEndDate().before(coupon.getStartDate())) {
			throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_VALUE_ENTERED,
					"coupon's expiration date cannot be set prior to its launch date");
		}
	}
//		if (coupon.getStartDate().after(now)) {
//			couponRepository.updateACoupon(coupon);
//			System.out.println("\n--The coupon was updated--\n");
//		
//		}else{
//	couponsDAO.updateACouponWithoutStartTime(coupon);
//	System.out.println("\n--The Coupon's details have been altered, not including it's start time--\n");
//	}
		
		@Transactional
		public void deleteCoupon(Coupon coupon) {
			couponRepository.delete(coupon);
			System.out.println("\n--The coupon was deleted--\n");
		
			if (!couponRepository.getCompanyCoupons(company)) {
				throw new CouponsSystemExceptions(SystemExceptions.ILLEGAL_ACTION_ATTEMPTED,
						"This coupon does not belong to this company");
			}
		
		}

	}
