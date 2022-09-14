/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.mapper.training.HrTdIspStudentPlacementFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author MUS
 */
@Stateless
public class HrInternshipStudsPlacementBean implements HrInternshipStudsPlacementBeanLocal {

    @EJB
    HrTdIspStudentPlacementFacade hrTdIspStudentPlacementFacade;

    @Inject
    HrTdIspStudentPlacement hrTdIspStudentPlacement;
    
    @Inject
    HrDepartments hrDepartments;
    
    @Inject
    HrTdIspStudentDetails hrTdIspStudentDetails;

    @Override
    public void create(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        hrTdIspStudentPlacementFacade.create(hrTdIspStudentPlacement);
    }

    @Override
    public List<HrTdIspStudentDetails> findAllStudentsName() {
        return hrTdIspStudentPlacementFacade.findAllStudentsName();
    }

    @Override
    public ArrayList<HrDepartments> findAllDepartmentsName() {
        return hrTdIspStudentPlacementFacade.findAllDepartmentsName();
    }

    @Override
    public HrTdIspStudentPlacement findbyStdId(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        return hrTdIspStudentPlacementFacade.findbyStdId(hrTdIspStudentPlacement);
    }

    @Override
    public void update(HrTdIspStudentPlacement hrTdIspStudentPlacement) {
        hrTdIspStudentPlacementFacade.edit(hrTdIspStudentPlacement);
    }

    @Override
    public HrTdIspStudentPlacement getSelectedRequest(int request) {
        return hrTdIspStudentPlacementFacade.getSelectedRequest(request);
    }

}
