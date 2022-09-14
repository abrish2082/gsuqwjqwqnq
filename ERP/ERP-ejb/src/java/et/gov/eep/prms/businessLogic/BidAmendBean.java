/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidDetailAmend;
import et.gov.eep.prms.mapper.PrmsBidAmendFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class BidAmendBean implements BidAmendBeanLocal {

    @EJB
    PrmsBidAmendFacade prmsBidAmendFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;

    @Override
    public List<PrmsBidDetail> getBidNo(String bidNo) {
        return prmsBidAmendFacade.getBidNo(bidNo);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<PrmsBid> getBidNoLst() {
        return prmsBidAmendFacade.getBidNoLst();
    }

    @Override
    public void save(PrmsBidAmend prmsBidAmend) {
        prmsBidAmendFacade.create(prmsBidAmend);
    }

    @Override
    public void edit(PrmsBidAmend prmsBidAmend) {
        prmsBidAmendFacade.edit(prmsBidAmend);
    }

    @Override
    public PrmsBid getBidNum(String string) {
        return prmsBidAmendFacade.getBidNums(string);
    }

    @Override
    public List<PrmsBidDetailAmend> getListOfBidAmendNum() {
        return prmsBidAmendFacade.getBidAmendNo();
    }

    @Override
    public List<PrmsBidDetailAmend> getBidNumb(String bidNum) {
        return prmsBidAmendFacade.getBidNumb(bidNum);
    }

    @Override
    public PrmsBidAmend getBidNumber(String bidNumer) {
        return prmsBidAmendFacade.getBidNumber(bidNumer);
    }

    @Override
    public List<PrmsBidAmend> getBidamendNo(String amnedNum) {
        return prmsBidAmendFacade.getBidamendNo(amnedNum);
    }

    @Override
    public List<PrmsBidAmend> getAmendNo(String toString) {
        return prmsBidAmendFacade.getBidAmended(toString);
    }

    @Override
    public List<PrmsBidAmend> searchBidNoAmnedNo(PrmsBidAmend prmsBidAmend) {
        return prmsBidAmendFacade.getBidNoAndAmendNo(prmsBidAmend);
    }

    @Override
    public PrmsBidAmend getSelectedRequest(String id) {
        return prmsBidAmendFacade.getBidAmnedId(id);
    }

    @Override
    public List<PrmsBidAmend> getBidAmendmentOnList() {
        return prmsBidAmendFacade.getBidAmendmentOnList();
    }

    @Override
    public String getNextAmendRegNo() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String amnedRegNo = null;
        String prefix = "AMEND-NO";
        int maxNo = 0;
        List<PrmsBidAmend> amendRegNoList = prmsBidAmendFacade.getNextAmendRegNo(prefix, eYear);
        for (int rowCount = 0; rowCount < amendRegNoList.size(); rowCount++) {
            amnedRegNo = amendRegNoList.get(rowCount).getAmendNo();
            String[] lastLcRegNoNos = amnedRegNo.split("-");
            String lastDatesPatern = lastLcRegNoNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo = maxNo + 1;
        amnedRegNo = "AMEND-NO-" + maxNo + "/" + eYear;
        return amnedRegNo;
    }

    @Override
    public String getNextClarificationSeq() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String calrNum = null;
        String prefix = "CLAR-NO";
        int maxNo = 0;
        List<PrmsBidAmend> amendRegNoList = prmsBidAmendFacade.getNextClarifNo(prefix, eYear);
        for (int rowCount = 0; rowCount < amendRegNoList.size(); rowCount++) {
            calrNum = amendRegNoList.get(rowCount).getClarificationNo();
            String[] lastLcRegNoNos = calrNum.split("-");
            String lastDatesPatern = lastLcRegNoNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo = maxNo + 1;
        calrNum = "CLAR-NO-" + maxNo + "/" + eYear;
        return calrNum;
    }

    @Override
    public List<PrmsBidAmend> getBidAmendNoList(String amendnu) {
        return prmsBidAmendFacade.getBidAmendNoList(amendnu);
    }

    @Override
    public List<PrmsBidAmend> getParamNameList() {
         return prmsBidAmendFacade.getParamNameList();
    }
}
