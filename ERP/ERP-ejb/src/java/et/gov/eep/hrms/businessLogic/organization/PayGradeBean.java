/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.mapper.lookup.HrLuGradesFacade;
import et.gov.eep.hrms.mapper.organization.HrSalaryScaleRangesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class PayGradeBean implements PayGradeBeanLocal {

    @EJB
    HrSalaryScaleRangesFacade hrSalaryScaleRangesFacade;
    @EJB
    HrLuGradesFacade hrLuGradesFacade;

    @Override
    public void create(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.create(hrSalaryScaleRanges);
    }

    @Override
    public void edit(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.edit(hrSalaryScaleRanges);
    }

    @Override
    public void SaveOrUpdate(HrSalaryScaleRanges hrSalaryScaleRanges) {
        hrSalaryScaleRangesFacade.saveOrUpdate(hrSalaryScaleRanges);
    }

    @Override
    public void save(HrLuGrades hrLuGrades) {
        hrLuGradesFacade.create(hrLuGrades);
    }

    @Override
    public void edit(HrLuGrades hrLuGrades) {
        hrLuGradesFacade.edit(hrLuGrades);
    }

    @Override
    public List<HrLuGrades> findall() {
        return hrLuGradesFacade.findAll();
    }

    @Override
    public HrSalaryScaleRanges findSalaryScale(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return hrSalaryScaleRangesFacade.findSalaryScale(hrSalaryScaleRanges);
    }

    @Override
    public HrSalaryScaleRanges findSalaryScaleById(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return hrSalaryScaleRangesFacade.findSalaryScaleById(hrSalaryScaleRanges);
    }

    @Override
    public HrSalaryScaleRanges findByGrade(HrLuGrades hrLuGrades) {
        return hrSalaryScaleRangesFacade.findByGrade(hrLuGrades);
    }

    @Override
    public ArrayList<HrLuGrades> searchByGrade(HrLuGrades hrLuGrades) {
        return hrLuGradesFacade.searchByGrade(hrLuGrades);
    }

    @Override
    public HrLuGrades getByGrade(HrLuGrades hrLuGrades) {
        return hrLuGradesFacade.getByGrade(hrLuGrades);
    }

}
