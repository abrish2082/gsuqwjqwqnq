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
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface HrinsurancePaymentLocal {

    public void saveorupdate(HrInsurancePayment hrInsurancePayment);

    public List<HrInsurancePayment> populateTableApprove(Status status, int userId);

    public List<HrInsurancePayment> loadFiltereddata(Status status, int userId);

    public List<HrInsurancePayment> populateTableReject(Status status, int userId);

    public List<HrInsurancePayment> loadFiltereddata(int status);

    public List<SelectItem> filterByStatus();

    public HrInsurancePayment getSelectedRequest(Integer id);

    public void saveUpdate(HrInsurancePayment get);

    public HrInsurancePayment getSelectedPayment(Integer id);

    public List loadpaymentrequest(int status);

    public List<HrInsuranceDiagnosisResult> empnamed(String daily, Integer status2);

    public List<HrInsuranceDiagnosisResult> empnamed(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2);

    public List<HrInsuranceDiagnosisResult> empname(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, Integer status1);

    public List<HrInsuranceDiagnosisResult> findbynamepermanentupdate(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String skill, Integer status2);

    public List<HrInsuranceDiagnosisResult> findbynamedaily1(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String daily, Integer status2);

    public List<HrInsurancePaymentDetail> fetchNewInsurancePayments();

    public HrInsurancePaymentDetail getSelectedPayment(String refNo);

    public List<HrInsurancePaymentDetail> fetchCPOInsurancePayments();

    public List<HrInsuranceDiagnosisResult> empname(HrInsuranceDiagnosisResult hrInsuranceDiagnosisResult, String employe, String daily, Integer status1);

    public void edit(HrInsurancePayment hrInsurancePayment);

}
