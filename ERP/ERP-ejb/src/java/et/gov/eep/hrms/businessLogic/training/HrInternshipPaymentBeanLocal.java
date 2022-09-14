/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspPayments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrInternshipPaymentBeanLocal {

    public void saveOrUpdate(HrTdIspPayments hrTdIspPayment);

    public HrTdIspPayments getSelectedRequest(int request);

    public List<HrTdIspStudentDetails> findBystatus();

    public void saveOrUpdate(HrTdIspStudentDetails hrTdIspStudentDetails);

    public List<HrTdIspStudentDetails> findBystatusp();

    public HrTdIspStudentDetails findByidObj(int hrTdIspStudentDetails);

    public List<HrTdIspStudents> findByYear(Integer years);

    public List<HrTdIspStudents> findByYear(HrTdIspStudents hrTdIspStudents);

    public List<HrTdIspStudents> findYear();

    public HrTdIspStudents findById(HrTdIspStudents hrTdIspStudents);

    public List<HrTdIspPayments> findByReferenceLitter();

    public List<HrTdIspPayments> findByLetter(String refereletter);

    public List<HrTdIspPayments> findAlls();

    public List<HrTdIspStudentDetails> findAll();

}
