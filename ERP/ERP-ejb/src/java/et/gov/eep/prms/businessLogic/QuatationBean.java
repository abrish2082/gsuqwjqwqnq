/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import et.gov.eep.prms.mapper.PrmsQuotationDetailFacade;
import et.gov.eep.prms.mapper.PrmsQuotationFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class QuatationBean implements QuatationBeanLocal {

    @EJB
    PrmsQuotationFacade prmsQuotationFacade;

    @EJB
    private PrmsQuotationDetailFacade prmsQuotationDetailFacade;

    @Override
    public List<PrmsQuotation> searchPrmsQuotation(PrmsQuotation prmsQuotation) {
        return prmsQuotationFacade.searchPrmsQuotation(prmsQuotation);
    }

    @Override
    public void create(PrmsQuotation prmsQuotation) {
        prmsQuotationFacade.create(prmsQuotation);
    }

    @Override
    public void update(PrmsQuotation prmsQuotation) {
        prmsQuotationFacade.edit(prmsQuotation);
    }

    @Override
    public void deleteContactPersonInfo(PrmsQuotationDetail prmsQuotationDetail) {
        prmsQuotationDetailFacade.remove(prmsQuotationDetail);
    }

    @Override
    public List<PrmsPurchaseRequest> searchPurchaseReqNo() {
        return prmsQuotationFacade.searchPurchaseReNo();
    }

    @Override
    public List<PrmsPurchaseRequest> getPurchaseRequestList() {
        return prmsQuotationFacade.getPurchaseRequestList();
    }

    @Override
    public List<PrmsPurchaseRequestDet> getRequestList(String prNumber) {
        return prmsQuotationFacade.getAwardDetailList(prNumber);
    }

    @Override
    public List<PrmsPurchaseRequestDet> getItemNam() {
        return prmsQuotationFacade.getItemName();
    }

    @Override
    public List<PrmsPurchaseRequestDet> getItemCode() {
        return prmsQuotationFacade.getItemCode();
    }

    @Override
    public List<PrmsPurchaseRequestDet> getPrNoItem() {
        return prmsQuotationDetailFacade.getPrNoItem();
    }

    @Override
    public List<PrmsPurchaseRequestDet> getPrNoService() {
        return prmsQuotationDetailFacade.getPrNoService();
    }

    @Override
    public List<PrmsPurchaseRequestDet> bidGetter(String itemName) {
        return prmsQuotationFacade.getBidDates(itemName);
    }

    @Override
    public ArrayList<PrmsPurchaseRequestDet> getItemList(PrmsPurchaseRequestDet prmsPurchaseRequest) {
        return prmsQuotationFacade.getItemList(prmsPurchaseRequest);
    }

    @Override
    public List<PrmsPurchaseRequestDet> getItemCod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PrmsPurchaseRequestDet> prNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        return prmsQuotationFacade.prNo(prmsPurchaseRequestDet);
    }

    @Override
    public ArrayList<PrmsPurchaseRequestDet> getNumber(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        return prmsQuotationFacade.getNumber(prmsPurchaseRequestDet);
    }

    @Override
    public PrmsPurchaseRequest getPrList(PrmsPurchaseRequest prmsPurchaseRequest) {
        return prmsQuotationFacade.getPrList(prmsPurchaseRequest);
    }

    @Override
    public ArrayList<PrmsPurchaseRequestDet> getPurchaseRquestNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        return prmsQuotationFacade.getPurchaseRquestNo(prmsPurchaseRequestDet);
    }

//    @Override
//    public ArrayList<PrmsQuotation> searchvendorName(PrmsQuotation prmsQuotation) {
//      return prmsQuotationFacade.searchvendName(prmsQuotation);
//    }
    @Override
    public ArrayList<PrmsPurchaseRequestDet> getMaterialCode(PrmsPurchaseRequestDet prmsPurchaseRequest) {//thhis is pr event//
        return prmsQuotationFacade.getMaterialCode(prmsPurchaseRequest);

    }

    @Override
    public List<PrmsPurchaseRequestDet> getMaterialCodes() {
        return prmsQuotationFacade.getmatCode();
    }

    @Override
    public PrmsPurchaseRequestDet getPrlst(String toString) {
        return prmsQuotationDetailFacade.getPrLsts(toString);
    }

    @Override
    public List<PrmsPurchaseReqService> getServicePrNo() {
        return prmsQuotationFacade.getServicePrNo();
    }

    @Override
    public PrmsQuotation getSelectedRequest(BigDecimal id) {
        return prmsQuotationFacade.getSelectedRequest(id);
    }

    @Override
    public List<PrmsPurchaseRequestDet> findAllRequestDet() {
        return prmsQuotationFacade.findAllRequestDet();
    }

    @Override
    public PrmsPurchaseRequestDet purchaseRequstNo(PrmsPurchaseRequestDet PrmsPurchaseRequestDet) {
        return prmsQuotationFacade.purchaseRequstNo(PrmsPurchaseRequestDet);
    }

    @Override
    public PrmsQuotation LastQuotationNo() {
        return prmsQuotationFacade.LastQuotationNo();
    }

    @Override
    public List<PrmsLuVatTypeLookup> getVat() {
        return prmsQuotationFacade.getVat();
    }

    @Override
    public PrmsSupplyProfile getSupplierVendor(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsQuotationFacade.getSupplierVendor(prmsSupplyProfile);
    }

    @Override
    public PrmsSupplyProfile findBySpplier(PrmsLuVatTypeLookup prmsVatTypeLookup) {
        return prmsQuotationFacade.findBySpplier(prmsVatTypeLookup);
    }

    public PrmsServiceAndWorkReg findById(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return prmsQuotationFacade.findById(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsPurchaseReqService> getPrService() {
        return prmsQuotationFacade.getPrService();
    }

    @Override
    public List<PrmsPurchaseRequest> getPershaseRquest() {
        return prmsQuotationFacade.getPershaseRquest();
    }

    @Override
    public ArrayList<PrmsPurchaseRequest> getPrNumber() {
        return prmsQuotationFacade.getPrNumber();
    }

    @Override
    public List<PrmsPurchaseRequest> getPrList() {
        return prmsQuotationFacade.getPrList();
    }

    @Override
    public List<PrmsPurchaseRequest> getPrListForService() {
        return prmsQuotationFacade.getPrListForService();
    }

    @Override
    public List<PrmsPurchaseRequest> getPrListForWorks() {
        return prmsQuotationFacade.getPrListForWorks();
    }

    @Override
    public List<PrmsSupplyProfile> FindBySuppID(PrmsQuotation prmsQuotation) {
        return prmsQuotationFacade.FindBySuppID(prmsQuotation);
    }

    @Override
    public List<PrmsQuotation> searchPrmsQuotation_(PrmsQuotation prmsQuotation) {
        return prmsQuotationFacade.searchPrmsQuotation_(prmsQuotation);
    }

    @Override
    public List<PrmsQuotation> searchPrmsQuotation() {
        return prmsQuotationFacade.searchPrmsQuotation();
    }

    @Override
    public List<PrmsQuotation> getColumnNameList() {
        return prmsQuotationFacade.getColumnNameList();
    }
}
