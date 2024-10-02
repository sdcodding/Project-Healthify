package com.hms.api.serviceimpl;

import java.time.LocalDate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hms.api.dao.MedicineDao;
import com.hms.api.entity.Medicine;
import com.hms.api.service.MedicineService;
import com.hms.api.validation.MedicineValidation;

/**
 * @author RAM
 *
 */
@Service
public class MedicineServiceImp implements MedicineService {

	@Autowired
	private MedicineDao medicineDao;

	String excludedRows = "";
	int totalRecordCount = 0;
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, String> validatedError = new HashMap<String, String>();
	Map<Integer, Map<String, String>> errorMap = new HashMap<Integer, Map<String, String>>();

	@Override
	public boolean addMedicine(Medicine medicine) {
		Date date = Date.valueOf(LocalDate.now());
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		medicine.setId(id);
		medicine.setMedicineAddedDateInStock(date);

		return medicineDao.addMedicine(medicine);
	}

	@Override
	public boolean deleteMedicineById(String id) {
		return medicineDao.deleteMedicineById(id);
	}

	@Override
	public Medicine getMedicineById(String id) {
		Medicine medicine = medicineDao.getMedicineById(id);

		return medicine;
	}

	@Override
	public Medicine updateMedicine(Medicine medicine) {
		return medicineDao.updateMedicine(medicine);
	}

	@Override
	public List<Medicine> getAllMedicine() {
		return medicineDao.getAllMedicine();
	}

	@Override
	public List<Medicine> getMedicinesByName(String medicineName) {
		return medicineDao.findByNameContainingIgnoreCase(medicineName);
	}

	@Override
	public Medicine getMedicineByName(String medicineName) {
		return medicineDao.findByName(medicineName);
	}

	@Override
	public List<Medicine> getMedicinesWithQuantityMoreThanZero(int quantity) {
		return medicineDao.findByQuantityGreaterThan(quantity);
	}

	@Override
	public Long getCountOfMedicineByDateAdded(String dateAdded) {
		return medicineDao.countByDateAdded(dateAdded);
	}

	@Override
	public Long getMedicinesTotalCount() {
		return medicineDao.getTotalCount();
	}

	@Override
	public List<Medicine> getTop5MedicineAddedByDate(String date) {
		return medicineDao.findTop5ByIdDesc(date);
	}

	public List<Medicine> readExcel(String filePath) {
		Workbook workbook = null;
		FileInputStream fis = null;
		List<Medicine> list = new ArrayList<>();
		Medicine medicine = null;

		try {
			fis = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(1);
			totalRecordCount = sheet.getLastRowNum();
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 0;

			while (rows.hasNext()) {

				Row row = rows.next();
				if (rowCount == 0) {
					rowCount++;
					continue;
				}
				medicine = new Medicine();
				Thread.sleep(1);
				String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
				medicine.setId(id);
				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();

					int column = cell.getColumnIndex();

					switch (column) {
					case 0: {
						
						break;
					}
					case 1: {
						break;
					}
					case 2: {
						break;
					}
					case 3: {
						break;
					}

					}

				}
				validatedError = MedicineValidation.validateProduct(medicine);
				if (validatedError == null || validatedError.isEmpty()) {
					list.add(medicine);

				} else {
					int rowNum = row.getRowNum() + 1;
					// excludedRows = excludedRows + rowNum + ",";

					errorMap.put(rowNum, validatedError);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;

	}

	@Override
	public Map<String, Object> uploadSheet(MultipartFile myFile) {
		File file = new File("src/main/resources");

		String absolutePath = file.getAbsolutePath();
		int[] arr;

		FileOutputStream fos = null;
		try {
			byte[] data = myFile.getBytes();

			fos = new FileOutputStream(new File(absolutePath + File.separator + myFile.getOriginalFilename()));
			fos.write(data);

			List<Medicine> list = readExcel(absolutePath + File.separator + myFile.getOriginalFilename());

			arr = medicineDao.uploadProductList(list);

			map.put("Total Record In Sheet", totalRecordCount);
			map.put("Uploaded Records In DB", arr[0]);
			map.put("Exists Records In DB", arr[1]);
			map.put("Total Excluded ", errorMap.size());
			map.put("Bad Record Row Number", errorMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
