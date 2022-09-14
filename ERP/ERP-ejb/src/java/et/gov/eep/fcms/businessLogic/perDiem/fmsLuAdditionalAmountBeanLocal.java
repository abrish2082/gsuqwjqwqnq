/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;

/**
 *
 * @author muller
 */
@Local
public interface fmsLuAdditionalAmountBeanLocal {

    public void create(FmsLuAdditionalAmount additionalAmount);

    public void create(FmsGoodWillingPayment fmsGoodWillingPayment);

    public void edit(FmsLuAdditionalAmount additionalAmount);

    public void edit(FmsGoodWillingPayment fmsGoodWillingPayment);

    public FmsLuAdditionalAmount searchLevel(FmsLuAdditionalAmount additionalAmount);

    public FmsLuAdditionalAmount search1(FmsLuAdditionalAmount additionalAmount);

    public FmsGoodWillingPayment getAddtionalData(FmsGoodWillingPayment fmsGoodWillingPayment);

    public FmsLuAdditionalAmount getById(FmsLuAdditionalAmount additionalAmount);

    public FmsGoodWillingPayment getByGWId(FmsGoodWillingPayment fmsGoodWillingPayment);

    public List<FmsGoodWillingPayment> searchAll();

    public List<FmsLuAdditionalAmount> findAddtionalList(FmsLuAdditionalAmount additionalAmount);

    public List<FmsGoodWillingPayment> searchAllData(FmsGoodWillingPayment goodWillingPayment);

    public List<FmsLuAdditionalAmount> searchLevelByParameter(FmsLuAdditionalAmount additionalAmount);

    public List<FmsLuAdditionalAmount> searchAllLevel();
    
    public List<FmsLuAdditionalAmount> getFmsLuAdditionalAmountListsByParameter(FmsLuAdditionalAmount additionalAmount);

    public List<ColumnNameResolver> getFmsLuAdditionalAmountColumnNameList();


}
