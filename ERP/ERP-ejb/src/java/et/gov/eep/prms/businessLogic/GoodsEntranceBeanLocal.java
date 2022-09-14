/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFileUpload;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.util.List;
import javax.ejb.Local;


@Local
public interface GoodsEntranceBeanLocal {

   

    void create(PrmsGoodsEntrance prmsGoodsEntrance);

    void update(PrmsGoodsEntrance prmsGoodsEntrance);

    public List<PrmsPortEntry> listOfPort();

    public List<PrmsGoodsEntrance> searchRegisNo(PrmsGoodsEntrance prmsGoodsEntrance);

    public List<PrmsContract> listOfContractNO();

    public PrmsGoodsEntrance getSelectedRequest(String id);

    public PrmsGoodsEntrance getLastRegNo();

    public List<PrmsLcRigistration> listOfLcNO();

    public List<FmsLuCurrency> getCurrencyName();

    public List<ComLuCountry> getCountryList();

    public HrDepartments getSelectDepartement(int key);

    public List<PrmsGoodsEntrance> findAllGoodsInfo();

    public List<PrmsGoodsEntrance> getGoodsLists();

    public List<PrmsGoodsEntrance> searchByGoodsNo(PrmsGoodsEntrance prmsGoodsEntrance);

    public void remove(PrmsFileUpload prmsFileUpload);

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract);

    public List<PrmsLcRigistrationAmend> getCheckLcAmended(PrmsLcRigistration prmsLcRigistration);

    public PrmsLcRigistrationAmend getLcAmendedInfo(PrmsLcRigistration prmsLcRigistration);
    
    public List<PrmsGoodsEntrance> getGoodsListsByParameter(PrmsGoodsEntrance prmsGoodsEntrance);

    public List<ColumnNameResolver> getColumnNameList();


    
}
