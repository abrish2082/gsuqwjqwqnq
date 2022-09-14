/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface PayGradeBeanLocal {

    public void create(HrSalaryScaleRanges hrSalaryScaleRanges);

    public void edit(HrSalaryScaleRanges hrSalaryScaleRanges);

    public void SaveOrUpdate(HrSalaryScaleRanges hrSalaryScaleRanges);

    public void save(HrLuGrades hrLuGrades);

    public void edit(HrLuGrades hrLuGrades);

    public List<HrLuGrades> findall();

    public HrSalaryScaleRanges findSalaryScale(HrSalaryScaleRanges hrSalaryScaleRanges);

    public HrSalaryScaleRanges findSalaryScaleById(HrSalaryScaleRanges hrSalaryScaleRanges);

    public HrSalaryScaleRanges findByGrade(HrLuGrades hrLuGrades);

    public ArrayList<HrLuGrades> searchByGrade(HrLuGrades hrLuGrades);

    public HrLuGrades getByGrade(HrLuGrades hrLuGrades);

}
