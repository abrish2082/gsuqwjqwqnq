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
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface OBIntegrationBeanLocal {

    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger);

    public FmsOperatingBudgetDetail fetchUsingDepartmentID(FmsLuBudgetYear fmsLuBudgetYear, HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger);

    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartmentandCurrenPerid(HrDepartments hrDepartments, FmsAccountingPeriod currentPeriod);

    public ArrayList<FmsOperatingBudgetDetail> fetchByDepartment(HrDepartments hrDepartments);

    public FmsOperatingBudgetDetail fetchUsingDepartmentAndGL(HrDepartments hrDepartments, FmsGeneralLedger fmsGeneralLedger);
}
