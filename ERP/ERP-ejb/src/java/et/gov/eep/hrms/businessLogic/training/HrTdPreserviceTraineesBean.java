/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
import et.gov.eep.hrms.mapper.training.HrTdPsvcTraineesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrTdPreserviceTraineesBean implements HrTdPreserviceTraineesBeanLocal {

    @EJB
    HrTdPsvcTraineesFacade hrTdPsvcTraineesFacade;

    @Override
    public List<HrLuEducLevels> findEducationLevel() {
        return hrTdPsvcTraineesFacade.findByLevel();
    }

    @Override
    public List<HrLuEducTypes> findByEducationQuality() {
        return hrTdPsvcTraineesFacade.findByDepartment();
    }

    @Override
    public void saveOrUpdate(HrTdPsvcTrainees hrTdPsvcTrainees) {
        hrTdPsvcTraineesFacade.saveOrUpdate(hrTdPsvcTrainees);
    }

    @Override
    public List<HrTdPsvcTrainees> findall() {
        return hrTdPsvcTraineesFacade.findByAllyear();
    }

    @Override
    public ArrayList<HrTdPsvcTrainees> findByyear(HrTdPsvcTrainees hrTdPsvcTrainees) {
        return hrTdPsvcTraineesFacade.findByyear1(hrTdPsvcTrainees);
    }

    @Override
    public HrTdPsvcTraineeDetails searchById(HrTdPsvcTraineeDetails hrTdPsvcDetails) {
        return hrTdPsvcTraineesFacade.searchIMGById(hrTdPsvcDetails);
    }
}
