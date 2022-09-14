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
import et.gov.eep.hrms.mapper.training.HrTdPsvcResultsFacade;
import et.gov.eep.hrms.mapper.training.HrTdPsvcTraineeDetailsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrTraineesResultBean implements HrTraineesResultBeanLocal {

    @EJB
    HrTdPsvcResultsFacade hrTdPsvcResultsFacade;

    @EJB
    HrTdPsvcTraineeDetailsFacade hrTdPsvcTraineeDetailsFacade;

    @Override
    public void saveOrUpdate(HrTdPsvcResults hrTdPsvcResults) {
        hrTdPsvcResultsFacade.saveOrUpdate(hrTdPsvcResults);
    }

    @Override
    public List<HrTdPsvcCourses> findByCourse() {
        return hrTdPsvcResultsFacade.findByTrainingCourse();
    }

    @Override
    public List<HrTdPsvcTraineeDetails> findTrainers() {
        return hrTdPsvcResultsFacade.findTrainers();
    }

    @Override
    public List<HrTdPsvcTraineeDetails> findByTrainerName(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        return hrTdPsvcResultsFacade.findByTrainerName(hrTdPsvcTraineeDetails);
    }

    @Override
    public List<HrTdPsvcTraineeDetails> findall(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        return hrTdPsvcTraineeDetailsFacade.findAll();
    }

    @Override
    public List<HrTdPsvcTrainees> findall() {
        return hrTdPsvcResultsFacade.findByAllyear();
    }

    @Override
    public ArrayList findBySemister(HrTdPsvcResults hrTdPsvcResults) {
        return hrTdPsvcResultsFacade.findBySemister(hrTdPsvcResults);
    }

    @Override
    public List<HrTdPsvcTrainees> findByyear(Integer years) {
        return hrTdPsvcResultsFacade.findByYear(years);
    }

    @Override
    public List<HrTdPsvcTraineeDetails> findAll(String code) {
        return hrTdPsvcResultsFacade.searchByBachCode(code);
    }

    @Override
    public HrTdPsvcTrainees findBYId(Integer id) {
      return hrTdPsvcResultsFacade.findBYId(id);
    }

    @Override
    public List<HrTdPsvcResults> findByYearBach(String code) {
       return hrTdPsvcResultsFacade.findByYearBach(code);
    }
}
