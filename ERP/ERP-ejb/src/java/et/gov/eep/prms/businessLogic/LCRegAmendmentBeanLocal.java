/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFileUpload;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsLcRegDetailAmendment;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface LCRegAmendmentBeanLocal {

    public List<PrmsLcRigistrationAmend> searchLCNoLCAmendNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend);

    public List<PrmsLcRigistrationAmend> searchAmendedLCByLCNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend);

    public PrmsLcRigistrationAmend getLCNumber(BigDecimal AmendedNo);

    public PrmsLcRigistrationAmend getLCAmendNo();

    public List<PrmsLcRigistration> getLCNoLst();

    public List<PrmsLcRegDetailAmendment> getListOfLCAmendNum();

    public void save(PrmsLcRigistrationAmend prmsLcRigistrationAmend);

    public void edit(PrmsLcRigistrationAmend prmsLcRigistrationAmend);

    public PrmsLcRigistrationAmend getSelectedAmedId(Integer id);

    public PrmsLcRigistration getLcNum(String lcNo);

    public List<PrmsContract> listOfContractNOAmend();

    public List<PrmsServiceProvider> listOfServiceNO();

    public List<ComLuCountry> getCountryList();

    public List<PrmsPortEntry> listOfPorts();

    public List<FmsLuCurrency> getCurrencyName();

    public List<PrmsLcRigistrationAmend> getLCAmendLists();

    public List<PrmsLcRigistrationAmend> searchByAmendNo(PrmsLcRigistrationAmend prmsLcRigistrationAmend);

    public List<PrmsLcRigistrationAmend> findLCAmendListByWfStatus(int status);

    public void remove(PrmsFileUpload prmsFileUpload);

    public List<PrmsForeignExchange> findForeign();

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract);

    public String getNextLcRegAmendNo();

    public List<PrmsLcRigistrationAmend> getLCAmendedNoListByLcId(PrmsLcRigistration prmsLcRigistration);

    public PrmsLcRigistrationAmend getLcAmendInfoByAmendedNo(String amendedNo);

    public List<PrmsLcRigistrationAmend> getParamNameList();

}
