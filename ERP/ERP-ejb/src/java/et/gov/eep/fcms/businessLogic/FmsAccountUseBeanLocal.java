/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author AB
 */
@Local
public interface FmsAccountUseBeanLocal {

    /**
     *
     * @param accountUse
     */
    public void create(FmsAccountUse accountUse);

    /**
     *
     * @param accountUse
     */
    public void edit(FmsAccountUse accountUse);

//    public void saveOrUpdate(FmsAccountUse accountUse);
    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    public List<FmsAccountUse> getChequePaymentVouchers(String bankCode, String date);

    /**
     *
     * @param bankCode
     * @param date
     * @return
     */
    public List<FmsAccountUse> getCashReceiptVouchers(String bankCode, String date);

    /**
     *
     * @param statmentDate
     * @param dateSatment
     * @param bankCode
     * @return
     */
    public String getGLBankReco(String statmentDate, String dateSatment, String bankCode);

    /**
     *
     * @param budgetYear
     * @param reportMonth
     * @return
     */
    public List getPreviousBalance(String budgetYear, String reportMonth);

    public List<FmsAccountUse> getlagerd(HashMap hashMap);

    public List<FmsAccountUse> getlagerd_SL(HashMap hashMap);

    public List<FmsAccountUse> getlagerd_GL(HashMap hashMap);

    public List<FmsAccountUse> getlagerd_Sys(HashMap hashMap);

    public List<FmsAccountUse> gettrilSL(HashMap hashMap);

    public ArrayList<FmsAccountUse> fetchCPOAccUse(FmsCashPaymentOrder fmsCashPaymentOrder);

    public ArrayList<FmsAccountUse> fetchCHPVAccUse(FmsChequePaymentVoucher fmsChequePaymentVoucher);
    
    public FmsAccountUse getAccUse(FmsAccountUse fmsAccountUse); 

}
