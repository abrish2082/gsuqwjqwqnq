/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractDetail;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsQuotation;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ContractInformationBeanLocal {

    public ArrayList<PrmsContract> searchContractByContractNo(PrmsContract papmsContract);

    public void update(PrmsContract papmsContract);

    public void create(PrmsContract papmsContract);

    public PrmsContract getContractNo(PrmsContract papmsContract);

    public List<HrDepartments> searchdeptName();

    public ArrayList<PrmsBid> bidNumberList();

    public ArrayList<PrmsBid> quotationNumberList();

    public List<PrmsSupplyProfile> VendorName();

//    public List<PrmsSupplyProfile> VendorNameWinner();//KOKO
    List<PrmsContract> searchContract(PrmsContract papmsContract);

    List<PrmsContract> searchContract(int status, int UserId);

    public void deletePrmsContractDetail(PrmsContractDetail prmsContractDetail);

    public PrmsContract getLastContractNo();

    public PrmsContract getSelectedRequest(BigDecimal id);

    public List<PrmsContract> ContractList();

    public ArrayList<PrmsBid> BidNoForCheck();

    public ArrayList<PrmsBid> vendorName();

    public ArrayList<PrmsAward> getsupplierlist(PrmsBid bidNo);

//    public ArrayList<PrmsSupplyProfile> getsupplierNamelist(PrmsAward awardNo);
    public List<PrmsQuotation> getListProforma();

    public List<PmsCreateProjects> getListProjects();

    public List<PrmsContract> ContractListStatus();

    public ArrayList<FmsLuCurrency> getCurrencylist();

    public void deletePrmsContractCurrDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail);

    public void updatePrmsContractCurrDetail(PrmsContractCurrencyDetail prmsContractCurrencyDetail);

    public PrmsBidAmend getBidAmendInfoByBidId(PrmsBidAmend prmsBidAmend);

    public List<PrmsBidAmend> checkBidFromAmended(PrmsBid eepBidReg);

    public List<PrmsContract> getParamNameList();

}
