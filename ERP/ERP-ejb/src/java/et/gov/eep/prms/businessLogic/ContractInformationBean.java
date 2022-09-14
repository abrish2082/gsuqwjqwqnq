/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.hrms.entity.organization.HrDepartments;
//import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsContractDetailFacade;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.mapper.PrmsContractCurrDetailFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Man
 */
@Stateless
public class ContractInformationBean implements ContractInformationBeanLocal {

    @EJB
    private PrmsContractDetailFacade prmsContractDetailFacade;
    @EJB
    private PrmsContractCurrDetailFacade prmsContractCurrencyDetailFacade;
    @EJB
    private PrmsSupplyProfileFacade supplyProfileFacade;
    @EJB
    private PrmsContractFacade prmsContractFacade;
//    @EJB   private PmsCreateProjectsFacade prmsCreateProjectFacade;

    @Override
    public ArrayList<PrmsContract> searchContractByContractNo(PrmsContract papmsContract) {
        return prmsContractFacade.searchContractByContractNo(papmsContract);
    }

    @Override
    public void update(PrmsContract papmsContract) {
        prmsContractFacade.edit(papmsContract);
//        prmsContractCurrencyDetailFacade.edit(papmsContract.getPrmsContractCurrencyDetailList());
//        updatePrmsContractCurrDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail)
        for (int i = 0; i < papmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
            updatePrmsContractCurrDetail(papmsContract.getPrmsContractCurrencyDetailList().get(i));
        }
    }

    @Override
    public void create(PrmsContract papmsContract) {
        prmsContractFacade.create(papmsContract);
    }

    @Override
    public PrmsContract getContractNo(PrmsContract papmsContract) {
        return prmsContractFacade.getContractNoinfo(papmsContract);
    }

    @Override
    public List<HrDepartments> searchdeptName() {
        return prmsContractFacade.searchdeptName();
    }

    @Override
    public ArrayList<PrmsBid> bidNumberList() {
        return prmsContractFacade.bidNumberList();
    }

    @Override
    public ArrayList<PrmsBid> quotationNumberList() {
        return prmsContractFacade.quotationNumberList();
    }

    @Override
    public List<PrmsSupplyProfile> VendorName() {
        return supplyProfileFacade.findAll();
    }

    @Override
    public List<PrmsContract> searchContract(PrmsContract papmsContract) {
        return prmsContractFacade.searchContract(papmsContract);
    }

    @Override
    public List<PrmsContract> searchContract(int status, int UserId) {
        return prmsContractFacade.searchContract(status, UserId);
    }

    @Override
    public void deletePrmsContractDetail(PrmsContractDetail prmsContractDetail) {
        prmsContractDetailFacade.remove(prmsContractDetail);
    }

    @Override
    public void updatePrmsContractCurrDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail) {
        prmsContractCurrencyDetailFacade.edit(prmsContractCurrencyDetail);
    }

    @Override
    public void deletePrmsContractCurrDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail) {
        prmsContractCurrencyDetailFacade.remove(prmsContractCurrencyDetail);
    }

    @Override
    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid prmsBid) {
        return prmsContractFacade.checkBidFromAmended(prmsBid);
    }

    @Override
    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend) {
        return prmsContractFacade.getBidAmendInfoByBidId(prmsBidAmend);
    }

    /**
     *
     * @return
     */
    @Override
    public PrmsContract getLastContractNo() {
        return prmsContractFacade.getLastContractNo();
    }

    @Override
    public PrmsContract getSelectedRequest(BigDecimal id) {
        return prmsContractFacade.getSelectedRequest(id);
    }

    @Override
    public List<PrmsContract> ContractList() {
        return prmsContractFacade.ContractListStatus();
    }

    @Override
    public List<PrmsContract> ContractListStatus() {
        return prmsContractFacade.ContractListStatus();
    }

    @Override
    public ArrayList<PrmsBid> BidNoForCheck() {
        return prmsContractFacade.BidNoForCheck();
    }

    @Override
    public ArrayList<PrmsBid> vendorName() {
        return prmsContractFacade.vendorName();
    }

    @Override
    public ArrayList<PrmsAward> getsupplierlist(PrmsBid bidNo) {
        return prmsContractFacade.getsupplierlist(bidNo);
    }

    @Override
    public List<PrmsQuotation> getListProforma() {
        return prmsContractFacade.getProforma();
    }

    @Override
    public List<PmsCreateProjects> getListProjects() {
        return prmsContractFacade.getListProjects();
    }

    @Override
    public ArrayList<FmsLuCurrency> getCurrencylist() {
        return prmsContractFacade.getCurrencylist();
    }

    @Override
    public List<PrmsContract> getParamNameList() {
         return prmsContractFacade.getParamNameList();
    }
}
