/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsServiceProviderDetail;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface InsuranceRegisterationBeanLocal {

    public List<PrmsInsuranceRequisition> searchByInsuranceNo(PrmsInsuranceRequisition insuranceregistration);

    public PrmsInsuranceRequisition getSelectedRow(String insuranceNo);

    public void create(PrmsInsuranceRequisition insuranceregistration);

    public void update(PrmsInsuranceRequisition insuranceregistration);

    public String getNextInsuranceNo();

    public ArrayList<PrmsServiceProvider> serviceProviderInsuranceList();

    public List<PrmsContract> contractNumList(int approvedStatus);

    public ArrayList<PrmsServiceProvider> serviceProviderListFrom();

    public ArrayList<PrmsServiceProviderDetail> fromServiceProDetail();

    public List<FmsLuCurrency> fromLuCurrency();

    public FmsLuCurrency autoXRate(FmsLuCurrency currency);

    public PrmsServiceProvider updateServFrom(PrmsServiceProvider prmsServiceProvider);

    public PrmsServiceProviderDetail updateServBranch(PrmsServiceProviderDetail prmsServiceProviderBranch);

    public List<PrmsServiceProviderDetail> getBankBranches(PrmsServiceProvider prmsServiceProviderTo);

    public List<PrmsInsuranceRequisition> getInsuRequestLists();

    public List<PrmsInsuranceRequisition> getParamNameList();

}
