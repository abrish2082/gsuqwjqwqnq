/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.mapper.training.HrTdIspStatusFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentDetailsFacade;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentPlacementFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrTdStatusBean implements HrTdStatusBeanLocal {

    @EJB
    HrTdIspStatusFacade hrTdIspStatusFacade;

    @EJB
    HrTdIspStudentPlacementFacade hrTdIspStudentPlacementFacade;

    @EJB
    HrTdIspStudentDetailsFacade hrTdIspStudentDetailsFacade;

    @Override
    public HrTdIspStudentDetails getSelectedRequest(int request) {
        return hrTdIspStatusFacade.getSelectedRequest(request);
    }

    @Override
    public void saveOrUpdate(HrTdIspStudentDetails hrTdIspStudentDetails) {
        hrTdIspStudentDetailsFacade.saveOrUpdate(hrTdIspStudentDetails);
    }

    @Override
    public void edit(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        hrTdIspStudentPlacementFacade.edit(hrTdIspStudentPlacement);
    }

    @Override
    public List<HrTdIspStudents> findByYear(Integer years) {
        return hrTdIspStatusFacade.findByYear(years);
    }

    @Override
    public List<HrTdIspStudents> findYear() {
        return hrTdIspStatusFacade.findyears();
    }

    @Override
    public List<HrTdIspStudentDetails> findBysemister(HrTdIspStudents hrTdIspStudents) {
        return hrTdIspStatusFacade.searchSemister(hrTdIspStudents);
    }

    @Override
    public List<HrTdIspStudentDetails> searchBySemister(HrTdIspStudents hrTdIspStudents) {
        return hrTdIspStatusFacade.searchBySemister(hrTdIspStudents);
    }
}
