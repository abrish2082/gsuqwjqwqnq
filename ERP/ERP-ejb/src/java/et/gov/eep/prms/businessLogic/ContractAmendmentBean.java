/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.mapper.PrmsContractAmendmentFacade;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsContractamendCurrency;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class ContractAmendmentBean implements ContractAmendmentBeanLocal {

    @EJB
    private PrmsContractAmendmentFacade prmsContractAmendmentFacade;
    @EJB
    private FmsLuCurrencyFacade fmsLuCurrencyFacade;

    @Override
    public void update(PrmsContractAmendment prmsContractAmendment) {
        prmsContractAmendmentFacade.edit(prmsContractAmendment);
    }

    @Override
    public void create(PrmsContractAmendment prmsContractAmendment) {
        prmsContractAmendmentFacade.create(prmsContractAmendment);
    }

    @Override
    public PrmsContract getContractNo(PrmsContract papmsContract) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrmsContractDetail> getContractNo(String contractNo) {
        return prmsContractAmendmentFacade.getContractNo(contractNo);
    }

    @Override
    public List<PrmsContractAmendment> getAmendNo(String toString) {
        return prmsContractAmendmentFacade.getBidAmend(toString);
    }

    @Override
    public PrmsContract getContractNumber(String toString) {
        return prmsContractAmendmentFacade.getContractNum(toString);
    }

    @Override
    public List<PrmsContractAmendment> getContractAmendmentNo(PrmsContractAmendment prmsContractAmendment) {
        return prmsContractAmendmentFacade.getContractAmendmentNo(prmsContractAmendment);
    }

    @Override
    public ArrayList<PrmsContractAmendment> getContractAmendmentNo(int status, int UserId) {
        return prmsContractAmendmentFacade.getContractAmendmentNo(status, UserId);
    }

    @Override
    public List<PrmsContract> getContractList() {
        return prmsContractAmendmentFacade.getContractList();
    }

    /**
     *
     * @return
     */
    public List<PrmsContractamendCurrency> getContractAmeList() {
        return prmsContractAmendmentFacade.getContractAmeList();
    }

    @Override
    public PrmsContractAmendment getBidNumber(String bidNumer) {
        return prmsContractAmendmentFacade.getBidNumber(bidNumer);
    }

    @Override
    public PrmsContractAmendment LastContractNo() {
        return prmsContractAmendmentFacade.LastContractNo();
    }

    @Override
    public PrmsContractAmendment getSelectedRequest(BigDecimal id) {
        return prmsContractAmendmentFacade.getSelectedRequest(id);
    }

    @Override
    public List<PrmsContractAmendment> getContAmendedNoByContNo(PrmsContract prmsContract) {
        return prmsContractAmendmentFacade.getContAmendedNoByContId(prmsContract);
    }

    @Override
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus(PrmsContract prmsContract) {
        return prmsContractAmendmentFacade.getContAmendedNoByContNoStatus(prmsContract);
    }

    @Override
    public List<PrmsContractAmendment> getContAmendedNoByContNoStatus() {
        return prmsContractAmendmentFacade.getContAmendedNoByContNoStatus();
    }

//    @Override
//    public List<PrmsContractCurrencyDetail> getByYearss(PrmsContract prmsContract) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public List<PrmsContractAmendment> getParamNameList() {
        return prmsContractAmendmentFacade.getParamNameList();
    }
}
