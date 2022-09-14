/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.mapper.PrmsSpecificationFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SpecificationBean implements SpecificationBeanLocal {

    @EJB
    PrmsSpecificationFacade specificationFacade;

    @Override
    public void create(PrmsSpecification specification) {
        specificationFacade.create(specification);
    }

    @Override
    public void update(PrmsSpecification specification) {
        specificationFacade.edit(specification);
    }

    @Override
    public PrmsSpecification getSpecification(PrmsSpecification specification) {
        return specificationFacade.getSpc(specification);
    }

    @Override
    public List<PrmsSpecification> searchSpecification(PrmsSpecification prmsSpecification) {
        return specificationFacade.searchSpecification(prmsSpecification);
    }

    @Override
    public ArrayList<PrmsBid> referenceNo() {
        return specificationFacade.referenceNo();
    }

    @Override
    public ArrayList<PrmsBid> bidNo(String string) {
        return specificationFacade.getbidNo(string);
    }

    @Override
    public List<PrmsBidDetail> getBidNoSep() {
        return specificationFacade.getBidNo();
    }

    @Override
    public ArrayList<PrmsBidDetail> getItemName() {
        return specificationFacade.getItemName();
    }

    @Override
    public PrmsBid getBid(PrmsBid prmsBid) {
        return specificationFacade.getBid(prmsBid);
    }

    @Override
    public List<PrmsBidDetail> getBidDet(PrmsBidDetail prmsBidDetail) {
        return specificationFacade.getBidDet(prmsBidDetail);
    }

//    @Override
//    public List<PrmsBidDetail> searchByBilDetail(PrmsBid prmsBidDetail) {
//       return specificationFacade.searchByBilDetail(prmsBidDetail);
//    }
    @Override
    public ArrayList<PrmsSpecification> searchBiddetailInfo(PrmsBidDetail prmsBidDetail) {
        return specificationFacade.getBidDetailInfo(prmsBidDetail);
    }

    @Override
    public PrmsSpecification getSelectedRequest(BigDecimal id) {
       return specificationFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsSpecification LastNo() {
       return specificationFacade.LastNo();
    }
}
