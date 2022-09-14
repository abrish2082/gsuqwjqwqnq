/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import et.gov.eep.prms.entity.PrmsRevenueContractDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mora1
 */
@Local
public interface RevenueContractBeanLocal {

    public void save(PrmsRevenueContarct prmsRevenueContarct);

    public List<FmsLuCurrency> currencyNameLists();

    public void edit(PrmsRevenueContarct prmsRevenueContarct);

    public List<PrmsRevenueContarct> searchAllRevContract();

    public void updateDetail(PrmsRevenueContractDetail prmsRevenueContractDetail);

    public List<PrmsRevenueContractDetail> howMPaidByProdOrService(String prodOrServName);

    public String generateRevContNo();

    public List<PrmsRevenueContarct> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue);

    public List<ColumnNameResolver> findColoumns();
    
}
