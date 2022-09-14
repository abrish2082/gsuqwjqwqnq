/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.insure;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.insurance.HrInsuranceDiagnosisResult;
import et.gov.eep.hrms.entity.insurance.HrInsurancePayment;
import et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail;
import et.gov.eep.hrms.mapper.insure.HrInsurancePaymentDetailFacade;
import et.gov.eep.hrms.mapper.insure.HrInsurancePaymentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class HrinsurancePayment implements HrinsurancePaymentLocal {

    @EJB
    HrInsurancePaymentFacade hrInsurancePaymentFacade;

    @EJB
    HrInsurancePaymentDetailFacade hrInsurancePaymentDetailFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void saveorupdate(HrInsurancePayment hrInsurancePayment) {
        hrInsurancePaymentFacade.saveOrUpdate(hrInsurancePayment);
    }

    @Override
    public List<HrInsurancePayment> loadFiltereddata(Status status, int userId) {
        return hrInsurancePaymentFacade.loadFiltereddata(status, userId);
    }
    @Override
    public List<HrInsurancePayment> populateTableReject(Status status, int userId) {
        return hrInsurancePaymentFacade.populateTableReject(status, userId);
    }


    @Override
    public List<HrInsurancePayment> populateTableApprove(Status status, int userId) {
        return hrInsurancePaymentFacade.populateTableApprove(status, userId);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Requested List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("4"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all List"));
        return selectItems;

    }

    @Override
    public HrInsurancePayment getSelectedRequest(Integer id) {
        return hrInsurancePaymentFacade.getSelectedRequest(id);
    }

    @Override
    public void saveUpdate(HrInsurancePayment hrInsurancePayment) {
        hrInsurancePaymentFacade.saveOrUpdate(hrInsurancePayment);
    }

    @Override
    public HrInsurancePayment getSelectedPayment(Integer idfill) {
        return hrInsurancePaymentFacade.getSelectedPayment(idfill);
    }

    @Override
    public List loadpaymentrequest(int status) {
        return hrInsurancePaymentFacade.loadpaymentrequest(status);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> empnamed(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2) {
        return hrInsurancePaymentFacade.empnamed(hrInsuranceDiagnosisResult, daily, status2);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> empnamed(String daily, Integer status2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrInsuranceDiagnosisResult> empname(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, Integer status1) {
        return hrInsurancePaymentFacade.empnamedd(hrInsuranceDiagnosisResult, employe, status1);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> findbynamepermanentupdate(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String skill, Integer status2) {
        return hrInsurancePaymentFacade.findbynamepermanentupdate(hrInsuranceDiagnosisResult, skill, status2);
    }

    @Override
    public List<HrInsuranceDiagnosisResult> findbynamedaily1(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2) {
        return hrInsurancePaymentFacade.findbynamedaily1(hrInsuranceDiagnosisResult, daily, status2);
    }

    @Override
    public List<HrInsurancePaymentDetail> fetchNewInsurancePayments() {
        return hrInsurancePaymentDetailFacade.fetchNewInsurancePayments();
    }

    @Override
    public HrInsurancePaymentDetail getSelectedPayment(String refNo) {
        return hrInsurancePaymentDetailFacade.getSelectedPayment(refNo);
    }

    @Override
    public List<HrInsurancePaymentDetail> fetchCPOInsurancePayments() {
        return hrInsurancePaymentDetailFacade.fetchCPOInsurancePayments();
    }

    @Override
    public List<HrInsuranceDiagnosisResult> empname(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, String daily, Integer status1) {
        return hrInsurancePaymentFacade.empnamedd(hrInsuranceDiagnosisResult, employe, daily, status1);
    }

    @Override
    public void edit(HrInsurancePayment hrInsurancePayment) {
        hrInsurancePaymentFacade.edit(hrInsurancePayment);
    }

    @Override
    public List<HrInsurancePayment> loadFiltereddata(int status) {
        return hrInsurancePaymentFacade.loadFiltereddata(status);
    }

}
