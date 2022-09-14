/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidderRegDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsPostQualification;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsBidderRegDetailFacade;
import et.gov.eep.prms.mapper.PrmsFinancialEvalResultFacade;
import et.gov.eep.prms.mapper.PrmsPostQualificationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class PostQualificationBean implements PostQualificationBeanLocal {

    @EJB
    PrmsPostQualificationFacade prmsPostQualificationFacade;
    @EJB
    PrmsBidFacade prmsBidFacade;
    @EJB
    PrmsFinancialEvalResultFacade prmsFinancialEvalResultFacade;

    @Override
    public void create(PrmsPostQualification prmsPostQualification) {
        prmsPostQualificationFacade.create(prmsPostQualification);
    }

    @Override
    public void update(PrmsPostQualification prmsPostQualification) {
        prmsPostQualificationFacade.edit(prmsPostQualification);
    }

    @Override
    public PrmsPostQualification getPQNos() {
        return prmsPostQualificationFacade.getPQNos();
    }

//    @Override
//    public PrmsPostQualification getReqPQN(Integer postId) {
//        return prmsPostQualificationFacade.getReqPQN(postId);
//    }

    @Override
    public List<PrmsPostQualification> searchByPostQ(PrmsPostQualification prmsPostQualification) {
        return prmsPostQualificationFacade.getPQNofind(prmsPostQualification);
    }

    @Override
    public List<PrmsPostQualification> searchAllPostQualification() {
        return prmsPostQualificationFacade.findAll();
    }

   
    @Override
    public List<PrmsFinancialEvalResult> listOfFinEvlNO() {
        return prmsPostQualificationFacade.getFinancialNoForPostQualifaction();
    }

    @Override
    public List<PrmsSupplyProfile> getItemNameLists(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile) {
        return  prmsPostQualificationFacade.getItemNameLists(prmsBid, prmsSupplyProfile);
    }

    @Override
    public List<PrmsFinancialEvlResultyDtl> getBidNameLists(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl,int nominatedRank) {
        return  prmsPostQualificationFacade.getBidNameLists(prmsFinancialEvlResultyDtl,nominatedRank);
    }


    @Override
    public List<PrmsBidDetail> getLotNames(PrmsBid prmsBid) {
        return prmsPostQualificationFacade.getLotNames(prmsBid);
    }

//    @Override
//    public List<PrmsFinancialEvlResultyDtl> getItemCodeList(MmsItemRegistration mmsItemRegistration) {
//        return prmsPostQualificationFacade.getItemCodeList(mmsItemRegistration);
//    }

    @Override
    public List<PrmsFinancialEvlResultyDtl> getLotNumberList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        return prmsPostQualificationFacade.getLotNumberList(prmsFinancialEvlResultyDtl);
    }

    @Override
    public List<PrmsFinancialEvlResultyDtl> getListofNextSupplier(PrmsFinancialEvalResult prmsFinancialEvalResult, int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        return  prmsPostQualificationFacade.getListofNextSupplier(prmsFinancialEvalResult,nominatedRank,prmsFinancialEvlResultyDtl);
    }

    @Override
    public List<PrmsFinancialEvlResultyDtl> getListOfLots(PrmsFinancialEvalResult prmsFinancialEvalResult,int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
         return  prmsPostQualificationFacade.getListOfLots(prmsFinancialEvalResult,nominatedRank,prmsFinancialEvlResultyDtl);
    }

    @Override
    public List<PrmsPostQualification> getPostRqLists() {
        return  prmsPostQualificationFacade.getPostRqLists();
    }

//    @Override
//    public List<PrmsFinancialEvlResultyDtl> getResultFormLists(PrmsFinancialEvalResult prmsFinancialEvalResult,int nominatedRank) {
//        return prmsPostQualificationFacade.getResultFormLists(prmsFinancialEvalResult,nominatedRank);
//    }
    
     @Override
    public List<PrmsFinancialEvlResultyDtl> getResultFormLists() {
        return prmsPostQualificationFacade.getResultFormLists();
    }

    @Override
    public List<MmsItemRegistration> getItemCodeList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl) {
        return prmsPostQualificationFacade.getItemCodeList(prmsFinancialEvlResultyDtl);
    }

    @Override
    public List<PrmsPostQualification> getParamNameList() {
       return prmsPostQualificationFacade.getParamNameList();
    }


}
