/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;


//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayment;
import et.gov.eep.fcms.mapper.loan.FmsPrincipalPaymentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>
/**
 *
 * @author Binyam
 */
@Stateless
public class FmsPrincipalPayScheduleBean implements FmsPrincipalPayScheduleBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
 @EJB
    FmsPrincipalPaymentFacade fmsPrincipalPaymentFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public void create(FmsPrincipalPayment fmsPrincipalPayment) {
        fmsPrincipalPaymentFacade.create(fmsPrincipalPayment);
    }

    /**
     *
     * @param fmsPrincipalPayment
     */
    @Override
    public void edit(FmsPrincipalPayment fmsPrincipalPayment) {
        fmsPrincipalPaymentFacade.edit(fmsPrincipalPayment);
    }

    /**
     *
     * @param fmsPrincipalPayment
     * @return
     */
    @Override
    public ArrayList<FmsPrincipalPayment> fetchschedule(FmsPrincipalPayment fmsPrincipalPayment) {
        return fmsPrincipalPaymentFacade.fetchPaySchedule(fmsPrincipalPayment);
    }

    /**
     *
     * @param fmsPrincipalPayment
     * @return
     */
    @Override
    public ArrayList<FmsPrincipalPayment> listschedule(FmsPrincipalPayment fmsPrincipalPayment) {
        return fmsPrincipalPaymentFacade.scheduleLister(fmsPrincipalPayment);
    }

    @Override
    public List<FmsPrincipalPayment> fetchPricipalPayment(FmsLoan fmsLoan) {
        return fmsPrincipalPaymentFacade.fetchPricipalPayment(fmsLoan);
    }

    @Override
    public void delete(FmsPrincipalPayment fmsPrincipalPayment) {
        fmsPrincipalPaymentFacade.remove(fmsPrincipalPayment);
    }
    //</editor-fold>
    
   

    /**
     *
     * @param fmsPrincipalPayment
     */
   
}
