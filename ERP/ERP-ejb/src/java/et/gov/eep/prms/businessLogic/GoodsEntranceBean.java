/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsFileUpload;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsFileUploadFacade;
import et.gov.eep.prms.mapper.PrmsGoodsEntranceFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationFacade;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GoodsEntranceBean implements GoodsEntranceBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsGoodsEntranceFacade goodsEntranceFacade;
    @EJB
    PrmsPortEntryFacade prmsPortEntryFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    @EJB
    PrmsLcRigistrationFacade prmsLcRigistrationFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    ComLuCountryFacade comLuCountryFacade;
    @EJB
    PrmsFileUploadFacade prmsFileUploadFacade;
    // </editor-fold>

//    @Override
//    public void save(PrmsGoodsEntrance goodsEntrance) {
//        goodsEntranceFacade.create(goodsEntrance);
//    }
    @Override
    public void create(PrmsGoodsEntrance prmsGoodsEntrance) {
        goodsEntranceFacade.create(prmsGoodsEntrance);
    }

    @Override
    public void update(PrmsGoodsEntrance prmsGoodsEntrance) {
        goodsEntranceFacade.edit(prmsGoodsEntrance);
    }

    @Override
    public List<PrmsPortEntry> listOfPort() {
        return prmsPortEntryFacade.findAll();
    }

    @Override
    public List<PrmsGoodsEntrance> searchRegisNo(PrmsGoodsEntrance prmsGoodsEntrance) {
        return goodsEntranceFacade.getRegistNo(prmsGoodsEntrance);
    }

    @Override
    public List<PrmsContract> listOfContractNO() {
        return prmsContractFacade.findAll();
    }

    @Override
    public PrmsGoodsEntrance getSelectedRequest(String id) {
        return goodsEntranceFacade.getSelectedId(id);
    }

    @Override
    public PrmsGoodsEntrance getLastRegNo() {
        return goodsEntranceFacade.getLastRegNo();
    }

    @Override
    public List<PrmsLcRigistration> listOfLcNO() {
        return goodsEntranceFacade.findApprovedLCNo();
    }

    @Override
    public List<FmsLuCurrency> getCurrencyName() {
        return fmsLuCurrencyFacade.findAll();
    }

    @Override
    public List<ComLuCountry> getCountryList() {
        return comLuCountryFacade.getCountries();
    }

    @Override
    public HrDepartments getSelectDepartement(int key) {
        return goodsEntranceFacade.getSelectDepartement(key);
    }

    @Override
    public List<PrmsGoodsEntrance> findAllGoodsInfo() {
        return goodsEntranceFacade.findAll();
    }

    @Override
    public List<PrmsGoodsEntrance> getGoodsLists() {
        return goodsEntranceFacade.getGoodsLst();
    }

    @Override
    public List<PrmsGoodsEntrance> searchByGoodsNo(PrmsGoodsEntrance prmsGoodsEntrance) {
        return goodsEntranceFacade.findGoodsNosss(prmsGoodsEntrance);
    }

    @Override
    public void remove(PrmsFileUpload prmsFileUpload) {
        prmsFileUploadFacade.remove(prmsFileUpload);
    }

    @Override
    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsCotract) {
        return goodsEntranceFacade.checkAmendOrNot(prmsCotract);
    }

    @Override
    public PrmsContractAmendment getContractAmendment(PrmsContract prmsCotract) {
        return goodsEntranceFacade.getContractAmendment(prmsCotract);
    }

    @Override
    public List<PrmsLcRigistrationAmend> getCheckLcAmended(PrmsLcRigistration prmsLcRigistration) {
        return goodsEntranceFacade.getCheckLcAmended(prmsLcRigistration);
    }

    @Override
    public PrmsLcRigistrationAmend getLcAmendedInfo(PrmsLcRigistration prmsLcRigistration) {
        return goodsEntranceFacade.getLcAmendedInfo(prmsLcRigistration);
    }
 @Override
    public List<PrmsGoodsEntrance> getGoodsListsByParameter(PrmsGoodsEntrance prmsGoodsEntrance) {
        return goodsEntranceFacade.getGoodsListsByParameter(prmsGoodsEntrance);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return goodsEntranceFacade.getColumnNameList();
    }
}
