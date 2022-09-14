/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
import et.gov.eep.hrms.entity.training.HrTdPsvcResults;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrTraineesResultBeanLocal {

    public void saveOrUpdate(HrTdPsvcResults hrTdPsvcResults);

    public List<HrTdPsvcCourses> findByCourse();

    public List<HrTdPsvcTraineeDetails> findTrainers();

    public List<HrTdPsvcTraineeDetails> findByTrainerName(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails);

    public List<HrTdPsvcTraineeDetails> findall(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails);

    public List<HrTdPsvcTrainees> findall();

    public ArrayList findBySemister(HrTdPsvcResults hrTdPsvcResults);

    public List<HrTdPsvcTrainees> findByyear(Integer years);

    public List<HrTdPsvcTraineeDetails> findAll(String code);

    public HrTdPsvcTrainees findBYId(Integer id);

    public List<HrTdPsvcResults> findByYearBach(String code);

}
