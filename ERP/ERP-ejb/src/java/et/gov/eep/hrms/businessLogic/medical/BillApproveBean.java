/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.medical.HrLocalMedInstitutions;
import et.gov.eep.hrms.entity.medical.HrLocalMedInvoices;
import et.gov.eep.hrms.mapper.medical.HrLocalMedInvoicesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Stateless
public class BillApproveBean implements BillApproveBeanLocal {

    @EJB
    HrLocalMedInvoicesFacade hrLocalMedInvoicesFacade;

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
        selectItems.add(new SelectItem(String.valueOf(0), "Load request list"));
        selectItems.add(new SelectItem(String.valueOf(1), "Load approved list"));
        selectItems.add(new SelectItem(String.valueOf(2), "Load rejected list"));
        return selectItems;
    }

    @Override
    public List<HrLocalMedInvoices> loadMedicalInvoice(int status) {
        return hrLocalMedInvoicesFacade.loadMedicalInvoice(status);
    }

    @Override
    public List<HrLocalMedInvoices> searchByStatus(int status) {
        return hrLocalMedInvoicesFacade.searchByStatus(status);
    }

    @Override
    public List<HrLocalMedInvoices> loadMedicalInvoiceList(Status status, int userId
    ) {
        return hrLocalMedInvoicesFacade.loadMedicalInvoiceList(status, userId);
    }

    @Override
    public HrLocalMedInvoices getSelectedMedInvoice(int invoice
    ) {
        return hrLocalMedInvoicesFacade.getSelectedMedInvoice(invoice);
    }

    @Override
    public List<HrLocalMedInvoices> findPreparedList() {
        return hrLocalMedInvoicesFacade.findPreparedList();
    }

}
