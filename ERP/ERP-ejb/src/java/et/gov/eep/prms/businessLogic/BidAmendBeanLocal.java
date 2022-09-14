/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidDetailAmend;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BidAmendBeanLocal {

    public List<PrmsBidDetail> getBidNo(String bidNo);

    public List<PrmsBid> getBidNoLst();

    public void save(PrmsBidAmend prmsBidAmend);

    public void edit(PrmsBidAmend prmsBidAmend);

    public PrmsBid getBidNum(String toString);

    public List<PrmsBidDetailAmend> getListOfBidAmendNum();

    public List<PrmsBidDetailAmend> getBidNumb(String bidNum);

    public PrmsBidAmend getBidNumber(String bidNumer);

    public List<PrmsBidAmend> getBidamendNo(String amnedNum);

    public List<PrmsBidAmend> getAmendNo(String toString);

    public List<PrmsBidAmend> searchBidNoAmnedNo(PrmsBidAmend prmsBidAmend);

    public PrmsBidAmend getSelectedRequest(String id);

    public List<PrmsBidAmend> getBidAmendmentOnList();

    public String getNextAmendRegNo();

    public String getNextClarificationSeq();

    public List<PrmsBidAmend> getBidAmendNoList(String amendnum);

    public List<PrmsBidAmend> getParamNameList();
}
