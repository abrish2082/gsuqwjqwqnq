/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
import et.gov.eep.prms.entity.PrmsSuppSpecificationUpload;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsSupplierSpecificationDtFacade;
import et.gov.eep.prms.mapper.PrmsSuppSpecificationFacade;
import et.gov.eep.prms.mapper.PrmsSuppSpecificationUploadFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * *****************************************************************************
 *
 * @author user
 *******************************************************************************
 */
@Stateless
public class SupplierSpecficationBean implements SupplierSpecficationBeanLocal {

    @EJB
    private PrmsSupplierSpecificationDtFacade prmsSupplierSpecificationDtFacade;
    @EJB
    private PrmsSuppSpecificationFacade prmsSuppSpecificationFacade;
    @EJB
    private PrmsSuppSpecificationUploadFacade prmsSuppSpecificationUploadFacade;

    /**
     * *************************************************************************
     *
     * @param prmsSuppSpecification
     * ************************************************************************
     */
    @Override
    public void create(PrmsSuppSpecification prmsSuppSpecification) {
        prmsSuppSpecificationFacade.create(prmsSuppSpecification);
    }

    /**
     * *************************************************************************
     *
     * @param prmsSuppSpecification
     * ************************************************************************
     */
    @Override
    public void update(PrmsSuppSpecification prmsSuppSpecification) {
        prmsSuppSpecificationFacade.edit(prmsSuppSpecification);
    }

    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
    @Override
    public List<PrmsSuppSpecification> searchSuppSpecification(PrmsSuppSpecification prmsSuppSpecification) {
        return prmsSuppSpecificationFacade.searchSuppSpecification(prmsSuppSpecification);
    }

    @Override
    public List<PrmsSuppSpecification> searchSuppSpecification() {
        return prmsSuppSpecificationFacade.searchSuppSpecification();
    }

    /**
     *
     * @param prmsSupplierSpecificationDt
     */
    @Override
    public void deleteSuppSpecDetailList(PrmsSupplierSpecificationDt prmsSupplierSpecificationDt) {
        prmsSupplierSpecificationDtFacade.remove(prmsSupplierSpecificationDt);
    }

    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
//    @Override
//    public PrmsSuppSpecification getCustomerFee(PrmsSuppSpecification prmsSuppSpecification) {
//        return prmsSuppSpecificationFacade.getCustomerFee(prmsSuppSpecification);
//    }
    /**
     *
     * @param prmsSuppSpecification
     * @return
     */
    @Override
    public ArrayList<PrmsSuppSpecification> searchPartyList(PrmsSuppSpecification prmsSuppSpecification) {
        return prmsSuppSpecificationFacade.searchPartyList(prmsSuppSpecification);
    }

    /**
     *
     * @return
     */
    @Override
    public List<PrmsSpecification> getBidNoSep() {
        return prmsSuppSpecificationFacade.getBidNo();
    }

    @Override
    public List<PrmsSpecification> getAwardDetailList(String matName) {
        return prmsSuppSpecificationFacade.getAwardDetailList(matName);
    }

    @Override
    public List<PrmsBid> getBidNoList() {
        return prmsSuppSpecificationFacade.getBidList();
    }

    @Override
    public List<PrmsPreminilaryEvalutionDt> getSupplierList(String supplierList) {
        return prmsSuppSpecificationFacade.getSupplierList(supplierList);
    }

    @Override
    public List<PrmsBidDetail> getItemList(String itemList) {
        return prmsSuppSpecificationFacade.getItemList(itemList);
    }

    @Override
    public List<PrmsBidDetail> findByType(MmsItemRegistration matName) {
        return prmsSuppSpecificationFacade.findByType(matName);
    }

    @Override
    public PrmsSuppSpecification getSelectedRequest(BigDecimal id) {
        return prmsSuppSpecificationFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsSuppSpecification LastSpecficationNo() {
        return prmsSuppSpecificationFacade.LastSpecficationNo();
    }

    @Override
    public PrmsBidDetail findByName(String name) {
        return prmsSuppSpecificationFacade.findByName(name);
    }

    @Override
    public ArrayList<MmsItemRegistration> itemList() {
        return prmsSuppSpecificationFacade.itemList();
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getSupplierList() {
        return prmsSuppSpecificationFacade.getSupplieList();
    }

    @Override
    public PrmsBid getBidType(String toString) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String toString) {
        return prmsSuppSpecificationFacade.getPrelinmarList(toString);
    }

    @Override
    public List<PrmsBidDetail> getQuotationDEtailList(String QuotationNo) {
        return prmsSuppSpecificationFacade.getQuotationDEtailList(QuotationNo);
    }

    @Override
    public List<PrmsPreminilaryEvalutionDt> getsupplierlist(String bidNo) {
        return prmsSuppSpecificationFacade.getsupplierlist(bidNo);
    }

    @Override
    public List<PrmsBidDetail> getBidDetailList(String bidNo) {
        return prmsSuppSpecificationFacade.getBidDetailList(bidNo);
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getBidList(
            PrmsPreminilaryEvaluation preminilaryEvaluation) {
        return prmsSuppSpecificationFacade.getBidList(preminilaryEvaluation);
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormAward() {
        return prmsSuppSpecificationFacade.BidNoFormAward();
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo) {
        return prmsSuppSpecificationFacade.getsupplierlist(bidNo);
    }

    @Override
    public ArrayList<PrmsAward> getAwardNo(PrmsBid bidNo) {
        return prmsSuppSpecificationFacade.getAwardNo(bidNo);
    }

    @Override
    public ArrayList<PrmsAwardDetail> getitemName(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSuppSpecificationFacade.getitemName(prmsSupplyProfile);
    }

    @Override
    public ArrayList<PrmsContractDetail> getitemNameFromContract(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSuppSpecificationFacade.getitemNameFromContract(prmsSupplyProfile);
    }

    @Override
    public ArrayList<PrmsContractDetail> getitemNameFromContract(PrmsContract prmsContract) {
        return prmsSuppSpecificationFacade.getitemNameFromContract(prmsContract);
    }

    @Override
    public void save(PrmsSuppSpecificationUpload prmsSuppSpecificationUpload) {
        prmsSuppSpecificationUploadFacade.create(prmsSuppSpecificationUpload);
    }

    @Override
    public void remove(PrmsSuppSpecificationUpload prmsSuppSpecificationUpload) {
        prmsSuppSpecificationUploadFacade.remove(prmsSuppSpecificationUpload);
    }

    @Override
    public PrmsContract getcontNum(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSuppSpecificationFacade.getcontNum(prmsSupplyProfile);
    }

    @Override
    public PrmsContract getcontNumber(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSuppSpecificationFacade.getcontNumber(prmsSupplyProfile);
    }

    @Override
    public List<PrmsBidAmend> checkAsBidAmended(PrmsBid prmsBid) {
        return prmsSuppSpecificationFacade.checkAsBidAmended(prmsBid);
    }

    @Override
    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract) {
        return prmsSuppSpecificationFacade.getContractAmendedInfoByContractId(prmsContract);
    }

    @Override
    public List<PrmsContractDetail> getitemNameFromContractAme(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSuppSpecificationFacade.getitemNameFromContractAme(prmsSupplyProfile);
    }

    @Override
    public List<PrmsSupplyProfile> findSuppliers() {
        return prmsSuppSpecificationFacade.findSuppliers();
    }

    @Override
    public List<PrmsSuppSpecification> getParamNameList() {
        return prmsSuppSpecificationFacade.getParamNameList();
    }
}
