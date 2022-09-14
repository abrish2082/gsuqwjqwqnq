/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.fcms.mapper.perDiem.FmsLuPerdimeRateFacade;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;

/**
 *
 * @author muller
 */
@Stateless
public class PerdimeRateBean implements PerdimeRateBeanLocal {

    @EJB
    FmsLuPerdimeRateFacade fmsLuPerdimeRateFacade;

    @Override
    public List<HrSalaryScaleRanges> findBylevel(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return fmsLuPerdimeRateFacade.findAll(hrSalaryScaleRanges);
    }

    @Override
    public HrSalaryScaleRanges getGradeDetail(HrSalaryScaleRanges hrSalaryScaleRanges) {
        return fmsLuPerdimeRateFacade.getGrade(hrSalaryScaleRanges);
    }

    @Override
    public void Creat(FmsLuPerdimeRate fmsLuPerdimeRate) {
        fmsLuPerdimeRateFacade.create(fmsLuPerdimeRate);
    }

    @Override
    public List<HrLuJobLevels> listoflevel(HrLuJobLevels hrLuJobLevels) {
        return fmsLuPerdimeRateFacade.getlevel(hrLuJobLevels);
    }

    @Override
    public List<HrSalaryScaleRanges> findByJobLevelId(HrLuJobLevels hrLuJobLevels) {
        return fmsLuPerdimeRateFacade.findByJobLevelId(hrLuJobLevels);
    }

    @Override
    public List<HrLuGrades> findByJobLevelId1(HrLuJobLevels hrLuJobLevels) {
        return fmsLuPerdimeRateFacade.findByJobLevelId1(hrLuJobLevels);
    }

    @Override
    public List<HrSalaryScaleRanges> findByJobLevelIdJoined(HrLuJobLevels hrLuJobLevels) {
        return fmsLuPerdimeRateFacade.findByJobLevelIdJoined(hrLuJobLevels);
    }

    @Override
    public List<HrJobTypes> findByJobLevelIdJoined1(HrLuJobLevels hrLuJobLevels) {
        return fmsLuPerdimeRateFacade.findByJobLevelIdJoined1(hrLuJobLevels);
    }

    @Override
    public List<FmsLuAdditionalAmount> findAddtionalList(FmsLuAdditionalAmount additionalAmount) {
        return fmsLuPerdimeRateFacade.listOfAdd(additionalAmount);
    }

    @Override
    public List<FmsLuPerdimeRate> findByLevelAndGrade(FmsLuPerdimeRate fmsLuPerdimeRate) {
        return fmsLuPerdimeRateFacade.searchbyGrade(fmsLuPerdimeRate);
    }

    @Override
    public List<FmsLuPerdimeRate> findPRByLevelAndGrade(BigDecimal levelId, HrLuGrades hrLuGrades) {
        return fmsLuPerdimeRateFacade.findPRByLevelAndGrade(levelId, hrLuGrades);
    }

    @Override
    public void edit(FmsLuPerdimeRate fmsLuPerdimeRate) {
        fmsLuPerdimeRateFacade.edit(fmsLuPerdimeRate);
    }

    @Override
    public FmsLuPerdimeRate getById(FmsLuPerdimeRate fmsLuPerdimeRate) {
        return fmsLuPerdimeRateFacade.getData(fmsLuPerdimeRate);
    }

    @Override
    public List<FmsLuPerdimeRate> findAll() {
        return fmsLuPerdimeRateFacade.findAll();
    }

    @Override
    public List<FmsLuPerdimeRate> findPRByLevel(BigDecimal levelId) {
        return fmsLuPerdimeRateFacade.findPRByLevel(levelId);
    }

}
