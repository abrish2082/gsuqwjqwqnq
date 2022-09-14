/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsContractamendCurrency;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ContractAmendmentBeanLocal {

    public void update(PrmsContractAmendment prmsContractAmendment);

    public void create(PrmsContractAmendment prmsContractAmendment);

    public PrmsContract getContractNo(PrmsContract papmsContract);

    public List<PrmsContractDetail> getContractNo(String ContractNo);

    public List<PrmsContractAmendment> getAmendNo(String toString);

    public PrmsContract getContractNumber(String toString);

    public List<PrmsContractAmendment> getContractAmendmentNo(PrmsContractAmendment prmsContractAmendment);
    
    public ArrayList<PrmsContractAmendment> getContractAmendmentNo(int status,int UserId);

    public List<PrmsContract> getContractList();

    public PrmsContractAmendment getBidNumber(String bidNumer);

    public PrmsContractAmendment LastContractNo();

    public PrmsContractAmendment getSelectedRequest(BigDecimal id);

    public List<PrmsContractAmendment> getContAmendedNoByContNo(PrmsContract prmsContract);
    
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus(PrmsContract prmsContract);
    
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus();
    
//    public List<PrmsContractCurrencyDetail> getByYearss(PrmsContract prmsContract);
    
    public List<PrmsContractamendCurrency> getContractAmeList();

    public List<PrmsContractAmendment> getParamNameList();
}
