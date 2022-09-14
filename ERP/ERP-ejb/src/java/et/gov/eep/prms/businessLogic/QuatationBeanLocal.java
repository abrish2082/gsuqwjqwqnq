/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface QuatationBeanLocal {

    public List<PrmsQuotation> searchPrmsQuotation(PrmsQuotation prmsQuotation);

    public void create(PrmsQuotation prmsQuotation);

    public void update(PrmsQuotation prmsQuotation);

    public void deleteContactPersonInfo(PrmsQuotationDetail prmsQuotationDetail);

    public List<PrmsPurchaseRequest> searchPurchaseReqNo();

    public List<PrmsPurchaseRequest> getPurchaseRequestList();

    public List<PrmsPurchaseRequestDet> getItemNam();

    public List<PrmsPurchaseReqService> getServicePrNo();

    public List<PrmsPurchaseRequestDet> getItemCode();

    public List<PrmsPurchaseRequestDet> getPrNoItem();

    public List<PrmsPurchaseRequestDet> getPrNoService();

    public List<PrmsPurchaseRequestDet> getItemCod();

    public List<PrmsPurchaseRequestDet> bidGetter(String itemName);

    public List<PrmsPurchaseRequestDet> getRequestList(String prNumber);

    public ArrayList<PrmsPurchaseRequestDet> getItemList(PrmsPurchaseRequestDet prmsPurchaseRequest);

    public ArrayList<PrmsPurchaseRequestDet> prNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet);

    public ArrayList<PrmsPurchaseRequestDet> getNumber(PrmsPurchaseRequestDet prmsPurchaseRequestDet);

    public PrmsPurchaseRequest getPrList(PrmsPurchaseRequest prmsPurchaseRequest);

    public ArrayList<PrmsPurchaseRequestDet> getPurchaseRquestNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet);

//    public ArrayList<PrmsQuotation> searchvendorName(PrmsQuotation prmsQuotation);
    public ArrayList<PrmsPurchaseRequestDet> getMaterialCode(PrmsPurchaseRequestDet prmsPurchaseRequest);//thhis is pr event//

    public List<PrmsPurchaseRequestDet> getMaterialCodes();

    public PrmsPurchaseRequestDet getPrlst(String toString);
//     public FmsLuSystem getSysDetail(FmsLuSystem fmsLuSystem);

    public PrmsQuotation getSelectedRequest(BigDecimal id);

    public List<PrmsPurchaseRequestDet> findAllRequestDet();

    public PrmsPurchaseRequestDet purchaseRequstNo(PrmsPurchaseRequestDet PrmsPurchaseRequestDet);

    public PrmsQuotation LastQuotationNo();

    public List<PrmsLuVatTypeLookup> getVat();

    public PrmsSupplyProfile getSupplierVendor(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsSupplyProfile findBySpplier(PrmsLuVatTypeLookup prmsVatTypeLookup);

    public PrmsServiceAndWorkReg findById(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsPurchaseReqService> getPrService();

    public List<PrmsPurchaseRequest> getPershaseRquest();

    public ArrayList<PrmsPurchaseRequest> getPrNumber();

    public List<PrmsPurchaseRequest> getPrList();

    public List<PrmsPurchaseRequest> getPrListForService();

    public List<PrmsPurchaseRequest> getPrListForWorks();

    public List<PrmsSupplyProfile> FindBySuppID(PrmsQuotation prmsQuotation);

    public List<PrmsQuotation> searchPrmsQuotation_(PrmsQuotation prmsQuotation);

    public List<PrmsQuotation> searchPrmsQuotation();

    public List<PrmsQuotation> getColumnNameList();
}
