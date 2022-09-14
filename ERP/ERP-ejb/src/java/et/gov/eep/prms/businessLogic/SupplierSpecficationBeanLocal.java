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
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsSuppSpecification;
//import et.gov.eep.prms.entity.PrmsBidSubmission;
//import et.gov.eep.prms.entity.PrmsContractFileUpload;
import et.gov.eep.prms.entity.PrmsSuppSpecificationUpload;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface SupplierSpecficationBeanLocal {

    public List<PrmsSpecification> getBidNoSep();

    public void create(PrmsSuppSpecification prmsSuppSpecification);

    public void update(PrmsSuppSpecification prmsSuppSpecification);

    List<PrmsSuppSpecification> searchSuppSpecification(PrmsSuppSpecification prmsSuppSpecification);

    List<PrmsSuppSpecification> searchSuppSpecification();

    public void deleteSuppSpecDetailList(PrmsSupplierSpecificationDt prmsSupplierSpecificationDt);

    ArrayList<PrmsSuppSpecification> searchPartyList(PrmsSuppSpecification prmsSuppSpecification);

    public List<PrmsSpecification> getAwardDetailList(String matName);

    public List<PrmsBid> getBidNoList();

    public List<PrmsPreminilaryEvalutionDt> getSupplierList(String toString);

    public PrmsBid getBidType(String toString);

    public List<PrmsPreminilaryEvaluation> getSupplierList();

    public List<PrmsBidDetail> getItemList(String toString);

    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String toString);

    public PrmsSuppSpecification getSelectedRequest(BigDecimal id);

    public PrmsSuppSpecification LastSpecficationNo();

    public List<PrmsBidDetail> findByType(MmsItemRegistration matName);

    public PrmsBidDetail findByName(String name);

    public ArrayList<MmsItemRegistration> itemList();

    public List<PrmsBidDetail> getQuotationDEtailList(String QuotationNo);

    public List<PrmsPreminilaryEvalutionDt> getsupplierlist(String bidNo);

    public List<PrmsBidDetail> getBidDetailList(String bidNo);

    public List<PrmsPreminilaryEvaluation> getBidList(PrmsPreminilaryEvaluation preminilaryEvaluation);

    public ArrayList<PrmsBid> BidNoFormAward();

    public ArrayList<PrmsSupplyProfile> getsupplierlist(PrmsBid bidNo);

    public ArrayList<PrmsAward> getAwardNo(PrmsBid bidNo);

//    public ArrayList<PrmsAwardDetail> getitemName(PrmsSupplyProfile prmsSupplyProfile);
    public ArrayList<PrmsAwardDetail> getitemName(PrmsSupplyProfile prmsSupplyProfile);

    public ArrayList<PrmsContractDetail> getitemNameFromContract(PrmsSupplyProfile prmsSupplyProfile);

    public void save(PrmsSuppSpecificationUpload prmsSuppSpecificationUpload);

    /**
     *
     * @param prmsSuppSpecificationUpload
     */
    public void remove(PrmsSuppSpecificationUpload prmsSuppSpecificationUpload);

    public PrmsContract getcontNum(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsContract getcontNumber(PrmsSupplyProfile prmsSupplyProfile);

    public ArrayList<PrmsContractDetail> getitemNameFromContract(PrmsContract prmsContract);

    public List<PrmsBidAmend> checkAsBidAmended(PrmsBid prmsBid);

    public PrmsContractAmendment getContractAmendedInfoByContractId(PrmsContract prmsContract);

    public List<PrmsContractDetail> getitemNameFromContractAme(PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsSupplyProfile> findSuppliers();

    public List<PrmsSuppSpecification> getParamNameList();
}
