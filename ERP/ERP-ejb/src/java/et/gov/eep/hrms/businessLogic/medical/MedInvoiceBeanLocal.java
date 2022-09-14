/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface MedInvoiceBeanLocal {

    public List<HrLocalMedInstitutions> getHospitalList();

    public List<HrLocalMedInstitutions> getPharmacyList();

    public ArrayList<HrEmployees> searchPatientByName(String hrEmployee);

    public HrEmployees getPatientByName(HrEmployees hrEmployees);

    public void save(HrLocalMedInvoices hrLocalMedInvoices);

    public void edit(HrLocalMedInvoices hrLocalMedInvoices);

    public void saveOrUpdate(HrLocalMedInvoices hrLocalMedInvoices);

    public List<HrLocalMedInvoices> findByName(String institutionName);

    public ArrayList<HrLocalMedInvoices> findAll();

    public ArrayList<HrLocalMedInvoices> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions);

    public List<SelectItem> filterByStatus();

    public List<HrLocalMedInvoices> loadMedicalInvoice(int status);

    public HrLocalMedInvoices getSelectedMedInvoice(int invoice);

    public List<HrLocalMedInstitutions> findByInstType(String instType);

    public List<HrLocalMedInstitutions> findByType();

    public List<HrLocalMedInvoices> loadMedicalInvoiceList(Status status, int userId);

    public List<HrLocalMedInvoices> loadInvoiceList(Status status, int userId);

    public List<HrLocalMedInvoices> loadReqMedInvoiceList(Status status, int userId);

    public List<HrLocalMedInvoices> loadApproveMedInvoiceList(Status status, int userId);

}
