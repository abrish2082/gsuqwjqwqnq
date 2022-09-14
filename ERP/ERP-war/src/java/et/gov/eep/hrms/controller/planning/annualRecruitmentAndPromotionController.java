/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.planning;

import et.gov.eep.hrms.businessLogic.planning.NeedRequestBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.organization.HrSalaryScaleRanges;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitments;
import et.gov.eep.hrms.integration.HrDepartmentsIntegrationBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Behailu
 */
@Named(value = "annualRecruitmentAndPromotionController")
@ViewScoped
public class annualRecruitmentAndPromotionController implements Serializable {

    /**
     * Creates a new instance of annualRecruitmentAndPromotionController
     */
//<editor-fold defaultstate="collapsed" desc="Initialization">
    public annualRecruitmentAndPromotionController() {
    }
    @Inject
            HrHrpRecruitmentRequest hrHrpRecruitmentRequest;
    
    @Inject
            HrHrpRecruitments hrHrpRecruitments;
    
    @Inject
            HrEmployees hrEmployees;
    
    @Inject
            HrDepartments hrDepartments;
    
    @Inject
            HrJobTypes hrJobTypes;
    
    @Inject
            HrLuGrades hrLuGrades;
    
    @Inject
            HrLuEducLevels hrLuEducLevels;
    
    @Inject
            HrSalaryScaleRanges hrSalaryScaleRanges;
    
    @EJB
            NeedRequestBeanLocal needRequestBeanLocal;
    
    @EJB
            HrDepartmentsIntegrationBeanLocal departmentBeanLocal;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="variables ">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-document";
    private boolean searchPage = true;
    private boolean newPage = false;
    Integer status = 0;
//</editor-fold>

}
