/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.ArrayList;
import javax.ejb.Stateless;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsFailsupplerAward;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.mapper.PrmsAwardDetailFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsAwardFacade;
import et.gov.eep.prms.mapper.PrmsFailsupplerAwardFacade;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class AwardBean implements AwardBeanLocal {

    @EJB
    PrmsFailsupplerAwardFacade prmsFailsupplerAwardFacade;
    @EJB
    PrmsAwardDetailFacade prmsAwardDetailFacade;
    @EJB
    private PrmsAwardFacade prmsAwardFacade;
    @EJB
    private PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    @EJB
    private PrmsBidFacade prmsBidFacade;

    @Override
    public ArrayList<PrmsAward> searchAwardByAwardId(PrmsAward prmsAward) {
        return prmsAwardFacade.searchAwardByAwardId(prmsAward);
    }

    @Override
    public void update(PrmsAward prmsAward) {
        prmsAwardFacade.edit(prmsAward);
    }

    @Override
    public void create(PrmsAward prmsAward) {
        prmsAwardFacade.create(prmsAward);

    }

    @Override
    public PrmsAward searchAwardNo(PrmsAward prmsAward) {
        return prmsAwardFacade.searchAwardNo(prmsAward);

    }

    @Override
    public PrmsAward getAwardNo(PrmsAward prmsAward) {
        return prmsAwardFacade.getAwardNoinfo(prmsAward);

    }

    @Override
    public ArrayList<PrmsSupplyProfile> searchVendorName(PrmsSupplyProfile eepVendorReg) {
        return prmsAwardFacade.searchVendorName(eepVendorReg);

    }
//
//    @Override
//    public List< EepVendorReg> findAll() {
//    return papmsAwardFacade.findAlls();
//    }

    @Override
    public List<PrmsSupplyProfile> findAll() {
        return prmsSupplyProfileFacade.findAll();
    }

    @Override
    public List<PrmsBid> findBidNo() {
        return prmsBidFacade.findAll();
    }

    @Override
    public PrmsAward getLastPaymentNo() {
        return prmsAwardFacade.getLastPaymentNo();
    }

    @Override
    public void deleteAwardDetail(PrmsAwardDetail prmsAwardDetail) {
        prmsAwardDetailFacade.remove(prmsAwardDetail);
    }

    @Override
    public List<PrmsAward> searchAward(PrmsAward prmsAward) {
        return prmsAwardFacade.searchAward(prmsAward);
    }

    @Override
    public PrmsAward getSelectedRequest(String id) {
        return prmsAwardFacade.getSelectedRequest(id);
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormFinancialResult() {
        return prmsAwardFacade.BidNoFormFinancialResult();
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo) {
        return prmsAwardFacade.getsupplierNameFromResult(bidNo);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        return prmsAwardFacade.getsupplierNameFromResult(bidNo);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierNameFromPostQual(PrmsBid bidNo) {
        return prmsAwardFacade.getsupplierNameFromPostQual(bidNo);
    }

    @Override
    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPrice(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsAwardFacade.getQuantityAndUnitPrice(prmsSupplyProfile);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getfailedSupplierNameFromFinc(PrmsBid bidNo) {
        return prmsAwardFacade.getfailedSupplierNameFromFinc(bidNo);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getfailedSupplierNameFromPost(PrmsBid bidNo) {
        return prmsAwardFacade.getFfailSupplierNameFromPostQual(bidNo);
    }

    @Override
    public void deleteprmsFailsupplerAward(PrmsFailsupplerAward prmsFailsupplerAward) {
        prmsFailsupplerAwardFacade.remove(prmsFailsupplerAward);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getFfailSupplierTechnicalEval(PrmsBid bidNo) {
        return prmsAwardFacade.getFfailSupplierTechnicalEval(bidNo);
    }

    @Override
    public ArrayList<PrmsAward> searchawardBystatus() {
    return prmsAwardFacade.searchawardBystatus();
    }

    @Override
    public ArrayList<PrmsFinancialEvaluaDetail> getQuantityAndUnitPriceForAward(PrmsAward prmsAward) {
        return prmsAwardFacade.getQuantityAndUnitPriceForAward(prmsAward);
    }

    @Override
    public List<PrmsAward> getParamNameList() {
       return prmsAwardFacade.getParamNameList();
    }

}
