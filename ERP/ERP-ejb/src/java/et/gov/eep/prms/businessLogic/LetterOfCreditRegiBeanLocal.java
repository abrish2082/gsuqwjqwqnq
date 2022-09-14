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
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface LetterOfCreditRegiBeanLocal {

    public void save(PrmsLcRigistration prmsLcRigistration);

    public void update(PrmsLcRigistration prmsLcRigistration);

    public List<PrmsLcRigistration> searchByLCregNo(PrmsLcRigistration prmsLcRigistration);

    public List<PrmsServiceProvider> listOfServiceNO();

    public List<PrmsContract> listOfContractNO();

    public List<PrmsSupplyProfile> listOflistOfSupplier();

    public PrmsLcRigistration getSelectedLCReg(Integer lcId);

    public PrmsLcRigistration getlastLCRegNo();

    public List<ComLuCountry> getCountryList();

    public List<PrmsPortEntry> listOfPorts();

    public List<FmsLuCurrency> getCurrencyName();

    public List<PrmsLcRigistration> findAllLcInfo();

    public List<PrmsLcRigistration> getLCLists();

    public void remove(PrmsFileUpload prmsFileUpload);

    public List<PrmsForeignExchange> findForeign();

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract);

    public String getNextLcRegNo();

    public List<PrmsLcRigistration> getParamNameList();
}
