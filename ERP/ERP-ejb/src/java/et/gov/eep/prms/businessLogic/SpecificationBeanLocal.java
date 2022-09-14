/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface SpecificationBeanLocal {

    public void create(PrmsSpecification prmsSpecification);

    public void update(PrmsSpecification prmsSpecification);

    List<PrmsSpecification> searchSpecification(PrmsSpecification prmsSpecification);

    PrmsSpecification getSpecification(PrmsSpecification specification);

    public ArrayList<PrmsBid> referenceNo();

    public ArrayList<PrmsBid> bidNo(String string);

    public List<PrmsBidDetail> getBidNoSep();

    public ArrayList<PrmsBidDetail> getItemName();

    public PrmsBid getBid(PrmsBid prmsBid);

    public List<PrmsBidDetail> getBidDet(PrmsBidDetail prmsBidDetail);
//    public List<PrmsBid> searchByBilDetail(PrmsBid prmsBidDetail);

    public ArrayList<PrmsSpecification> searchBiddetailInfo(PrmsBidDetail prmsBidDetail);
     public PrmsSpecification getSelectedRequest(BigDecimal id) ;
      public PrmsSpecification LastNo();
}
