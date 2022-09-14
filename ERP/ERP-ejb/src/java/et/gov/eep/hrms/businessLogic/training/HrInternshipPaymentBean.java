/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspPayments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.mapper.training.HrTdIspPaymentFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentDetailsFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrInternshipPaymentBean implements HrInternshipPaymentBeanLocal {

    @EJB
    HrTdIspPaymentFacade hrInternshipPaymentfacade;

    @EJB
    HrTdIspStudentDetailsFacade hrTdIspStudentDetailsFacade;

    @EJB
    HrTdIspStudentsFacade hrTdIspStudentsFacade;

    @Override
    public void saveOrUpdate(HrTdIspPayments hrTdIspPayment) {
        hrInternshipPaymentfacade.saveOrUpdate(hrTdIspPayment);
    }

    @Override
    public HrTdIspPayments getSelectedRequest(int request) {
        return hrInternshipPaymentfacade.getSelectedRequest(request);
    }

    @Override
    public List<HrTdIspStudentDetails> findBystatus() {
        return hrInternshipPaymentfacade.findBystatus();
    }

    @Override
    public void saveOrUpdate(HrTdIspStudentDetails hrTdIspStudentDetails) {
        hrTdIspStudentDetailsFacade.saveOrUpdate(hrTdIspStudentDetails);
    }

    @Override
    public List<HrTdIspStudentDetails> findBystatusp() {
        return hrInternshipPaymentfacade.findBystatusp();
    }

    @Override
    public HrTdIspStudentDetails findByidObj(int hrTdIspStudentDetails) {
        return hrInternshipPaymentfacade.findByidObj(hrTdIspStudentDetails);
    }

    @Override
    public List<HrTdIspStudents> findByYear(Integer years) {
        return hrInternshipPaymentfacade.findByYear(years);
    }

    @Override
    public List<HrTdIspStudents> findByYear(HrTdIspStudents hrTdIspStudents) {
        return hrInternshipPaymentfacade.findByYear(hrTdIspStudents);
    }

    @Override
    public List<HrTdIspStudents> findYear() {
        return hrInternshipPaymentfacade.findyears();
    }

    @Override
    public HrTdIspStudents findById(HrTdIspStudents hrTdIspStudents) {
        return hrInternshipPaymentfacade.findById(hrTdIspStudents);
    }

    @Override
    public List<HrTdIspStudentDetails> findAll() {
        return hrInternshipPaymentfacade.findAlls();
    }

    @Override
    public List<HrTdIspPayments> findByReferenceLitter() {
        return hrInternshipPaymentfacade.findByLitter();
    }

    @Override
    public List<HrTdIspPayments> findByLetter(String refereletter) {
        return hrInternshipPaymentfacade.findByLetter(refereletter);
    }

    @Override
    public List<HrTdIspPayments> findAlls() {
        return hrInternshipPaymentfacade.findAll();
    }
}
