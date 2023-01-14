package com.hms.api.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hms.api.dao.MedicineCompanyDao;
import com.hms.api.entity.MedicineCompany;

/**
 * @author RAM
 *
 */
@Repository
public class MedicineCompanyDaoIMPL implements MedicineCompanyDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public List<MedicineCompany> findByNameContainingIgnoreCase(String companyName) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicineCompany findByName(String companyName) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long countByRegisterdate(String dateAdded) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicineCompany> findTop5ByIdDesc(String date) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicineCompany addMedicineCompany(MedicineCompany medicineCompany) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteMedicineCompanyById(String id) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public MedicineCompany getMedicineCompanyById(String id) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicineCompany updateMedicineCompany(MedicineCompany medicineCompany) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicineCompany> getAllMedicineCompanys() {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
