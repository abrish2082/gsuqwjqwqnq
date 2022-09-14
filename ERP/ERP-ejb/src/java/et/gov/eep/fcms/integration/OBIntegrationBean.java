/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.integration;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudgetDetailFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class OBIntegrationBean implements OBIntegrationBeanLocal {

    @EJB
    FmsOperatingBudgetDetailFacade fmsOperatingBudgetDetailFacade;

    @Override
    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {
        return fmsOperatingBudgetDetailFacade.fetchByDepartmentID(fmsLuBudgetYear, hrDepartments, fmsGeneralLedger);
    }

    @Override
    public FmsOperatingBudgetDetail fetchUsingDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {
        return fmsOperatingBudgetDetailFacade.fetchUsingDepartmentID(fmsLuBudgetYear, hrDepartments, fmsGeneralLedger);
    }

    @Override
    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentandCurrenPerid(HrDepartments hrDepartments, FmsAccountingPeriod currentPeriod) {
        return fmsOperatingBudgetDetailFacade.fetchByDepartmentandCurrenPerid(hrDepartments, currentPeriod);
    }

    @Override
    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartment(HrDepartments hrDepartments) {
        return fmsOperatingBudgetDetailFacade.fetchByDepartment(hrDepartments);
    }

    @Override
    public FmsOperatingBudgetDetail fetchUsingDepartmentAndGL(HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger) {
        return fmsOperatingBudgetDetailFacade.fetchUsingDepartmentAndGL(hrDepartments, fmsGeneralLedger);
    }

}
