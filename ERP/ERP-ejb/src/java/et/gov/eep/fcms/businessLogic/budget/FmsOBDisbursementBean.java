/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import et.gov.eep.fcms.mapper.budget.FmsObDisbursementFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Me
 */
@Stateless
public class FmsOBDisbursementBean implements FmsOBDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
@EJB
    FmsObDisbursementFacade fmsOBDisbursementBeanFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
@Override
    public void create(FmsObDisbursement fmsObDisbursement) {
        fmsOBDisbursementBeanFacade.saveOrUpdate(fmsObDisbursement);
    }
    
    @Override
    public void edit(FmsObDisbursement fmsObDisbursement) {
        fmsOBDisbursementBeanFacade.edit(fmsObDisbursement);
    }

    @Override
    public ArrayList<FmsObDisbursement> fetchDisbursedOB(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        return fmsOBDisbursementBeanFacade.fetchDisbursedOB(fmsOperatingBudgetDetail);
    }

    @Override
    public FmsObDisbursement fetchOBDisbByTaskId(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        return fmsOBDisbursementBeanFacade.fetchOBDisbByTaskId(fmsOperatingBudgetTasks);
    }

    @Override
    public FmsObDisbursement fetchOBDisbByID(FmsObDisbursement fmsObDisbursement) {
        return fmsOBDisbursementBeanFacade.fetchOBDisbByID(fmsObDisbursement);
    }

    //</editor-fold>
    
    

    
}
