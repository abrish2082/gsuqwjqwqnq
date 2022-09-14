/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.mapper.perDiem.FmsGoodWillingPaymentFacade;
import et.gov.eep.fcms.mapper.perDiem.FmsLuAdditionalAmountFacade;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;

/**
 *
 * @author muller
 */
@Stateless
public class fmsLuAdditionalAmountBean implements fmsLuAdditionalAmountBeanLocal {

    @EJB
    FmsLuAdditionalAmountFacade additionalAmountFacade;

    @EJB
    FmsGoodWillingPaymentFacade goodWillingPaymentFacade;

    @Override
    public void create(FmsLuAdditionalAmount additionalAmount) {
        additionalAmountFacade.create(additionalAmount);
    }

    @Override
    public void edit(FmsLuAdditionalAmount additionalAmount) {
        additionalAmountFacade.edit(additionalAmount);
    }

    @Override
    public void create(FmsGoodWillingPayment fmsGoodWillingPayment) {
        goodWillingPaymentFacade.create(fmsGoodWillingPayment);
    }

    @Override
    public void edit(FmsGoodWillingPayment fmsGoodWillingPayment) {
        goodWillingPaymentFacade.edit(fmsGoodWillingPayment);
    }

    @Override
    public FmsLuAdditionalAmount searchLevel(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.searchLevel(additionalAmount);
    }

    @Override
    public List<FmsLuAdditionalAmount> findAddtionalList(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.listOfAdd(additionalAmount);
    }

    @Override
    public FmsLuAdditionalAmount search1(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.search1(additionalAmount);
    }

    @Override
    public List<FmsGoodWillingPayment> searchAll() {
        return goodWillingPaymentFacade.findAll();
    }

    @Override
    public FmsGoodWillingPayment getAddtionalData(FmsGoodWillingPayment fmsGoodWillingPayment) {
        return additionalAmountFacade.getAddtionalData(fmsGoodWillingPayment);
    }

    @Override
    public FmsLuAdditionalAmount getById(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.getdata(additionalAmount);
    }

    @Override
    public FmsGoodWillingPayment getByGWId(FmsGoodWillingPayment fmsGoodWillingPayment) {
        return additionalAmountFacade.getByGWId(fmsGoodWillingPayment);
    }

    @Override
    public List<FmsGoodWillingPayment> searchAllData(FmsGoodWillingPayment goodWillingPayment) {
        return goodWillingPaymentFacade.findAll();
    }

    @Override
    public List<FmsLuAdditionalAmount> searchLevelByParameter(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.searchLevelByParameter(additionalAmount);
    }

    @Override
    public List<FmsLuAdditionalAmount> searchAllLevel() {
        return additionalAmountFacade.findAll();
    }
   public List<FmsLuAdditionalAmount> getFmsLuAdditionalAmountListsByParameter(FmsLuAdditionalAmount additionalAmount) {
        return additionalAmountFacade.getFmsLuAdditionalAmountListsByParameter(additionalAmount);
    }

    @Override
    public List<ColumnNameResolver> getFmsLuAdditionalAmountColumnNameList() {
        return additionalAmountFacade.getFmsLuAdditionalAmountColumnNameList();
    }
}
