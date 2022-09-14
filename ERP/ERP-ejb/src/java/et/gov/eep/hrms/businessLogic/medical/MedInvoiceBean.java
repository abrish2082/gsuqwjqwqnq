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
import et.gov.eep.hrms.mapper.medical.HrLocalMedInvoicesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class MedInvoiceBean implements MedInvoiceBeanLocal {

    @EJB
    HrLocalMedInvoicesFacade hrLocalMedInvoicesFacade;

    @Override
    public List<HrLocalMedInstitutions> getHospitalList() {
        return hrLocalMedInvoicesFacade.getHospitalList();
    }

    @Override
    public List<HrLocalMedInstitutions> getPharmacyList() {
        return hrLocalMedInvoicesFacade.getPharmacyList();
    }

    @Override
    public ArrayList<HrEmployees> searchPatientByName(String hrEmployee) {
        return hrLocalMedInvoicesFacade.searchPatientByName(hrEmployee);
    }

    @Override
    public HrEmployees getPatientByName(HrEmployees hrEmployees) {
        return hrLocalMedInvoicesFacade.getPatientByName(hrEmployees);
    }

    @Override
    public void save(HrLocalMedInvoices hrLocalMedInvoices) {
        hrLocalMedInvoicesFacade.create(hrLocalMedInvoices);
    }

    @Override
    public void edit(HrLocalMedInvoices hrLocalMedInvoices) {
        hrLocalMedInvoicesFacade.edit(hrLocalMedInvoices);
    }

    @Override
    public void saveOrUpdate(HrLocalMedInvoices hrLocalMedInvoices) {
        hrLocalMedInvoicesFacade.saveOrUpdate(hrLocalMedInvoices);
    }

    @Override
    public List<HrLocalMedInvoices> findByName(String institutionName) {
        return hrLocalMedInvoicesFacade.findByName(institutionName);
    }

    @Override
    public ArrayList<HrLocalMedInvoices> findAll() {
        return hrLocalMedInvoicesFacade.findAll();
    }

    @Override
    public ArrayList<HrLocalMedInvoices> findByInstitutionName(HrLocalMedInstitutions hrLocalMedInstitutions) {
        return hrLocalMedInvoicesFacade.findByInstitutionName(hrLocalMedInstitutions);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all List"));
        return selectItems;
    }

    @Override
    public List<HrLocalMedInvoices> loadMedicalInvoice(int status) {
        return hrLocalMedInvoicesFacade.loadMedicalInvoice(status);
    }

    @Override
    public List<HrLocalMedInvoices> loadMedicalInvoiceList(Status status, int userId) {
        return hrLocalMedInvoicesFacade.loadMedicalInvoiceList(status, userId);
    }

    @Override
    public List<HrLocalMedInvoices> loadInvoiceList(Status status, int userId) {
        return hrLocalMedInvoicesFacade.loadInvoiceList(status, userId);
    }

    @Override
    public HrLocalMedInvoices getSelectedMedInvoice(int invoice) {
        return hrLocalMedInvoicesFacade.getSelectedMedInvoice(invoice);
    }

    @Override
    public List<HrLocalMedInstitutions> findByType() {
        return hrLocalMedInvoicesFacade.findByType();
    }

    @Override
    public List<HrLocalMedInstitutions> findByInstType(String instType) {
        return hrLocalMedInvoicesFacade.findByInstType(instType);
    }
    
    @Override
    public List<HrLocalMedInvoices> loadReqMedInvoiceList(Status status, int userId) {
       return hrLocalMedInvoicesFacade.loadReqMedInvoiceList(status, userId);
    }
    
    @Override
    public List<HrLocalMedInvoices> loadApproveMedInvoiceList(Status status, int userId) {
      return hrLocalMedInvoicesFacade.loadApproveMedInvoiceList(status, userId);
    }

}
