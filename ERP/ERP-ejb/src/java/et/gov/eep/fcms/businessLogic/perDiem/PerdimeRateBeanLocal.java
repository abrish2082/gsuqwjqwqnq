/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;

/**
 *
 * @author muller
 */
@Local
public interface PerdimeRateBeanLocal {

    public void Creat(FmsLuPerdimeRate fmsLuPerdimeRate);

    public void edit(FmsLuPerdimeRate fmsLuPerdimeRate);

    public HrSalaryScaleRanges getGradeDetail(HrSalaryScaleRanges hrSalaryScaleRanges);

    public FmsLuPerdimeRate getById(FmsLuPerdimeRate fmsLuPerdimeRate);

    public List<HrSalaryScaleRanges> findBylevel(HrSalaryScaleRanges hrSalaryScaleRanges);

    public List<HrLuJobLevels> listoflevel(HrLuJobLevels hrLuJobLevels);

    public List<HrSalaryScaleRanges> findByJobLevelId(HrLuJobLevels hrLuJobLevels);

    public List<HrLuGrades> findByJobLevelId1(HrLuJobLevels hrLuJobLevels);

    public List<HrSalaryScaleRanges> findByJobLevelIdJoined(HrLuJobLevels hrLuJobLevels);

    public List<HrJobTypes> findByJobLevelIdJoined1(HrLuJobLevels hrLuJobLevels);

    public List<FmsLuAdditionalAmount> findAddtionalList(FmsLuAdditionalAmount additionalAmount);

    public List<FmsLuPerdimeRate> findByLevelAndGrade(FmsLuPerdimeRate fmsLuPerdimeRate);

    public List<FmsLuPerdimeRate> findPRByLevel(BigDecimal levelId);

    public List<FmsLuPerdimeRate> findPRByLevelAndGrade(BigDecimal levelId, HrLuGrades hrLuGrades);

    public List<FmsLuPerdimeRate> findAll();

}
