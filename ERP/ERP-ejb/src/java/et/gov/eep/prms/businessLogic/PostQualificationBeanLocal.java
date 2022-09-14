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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PostQualificationBeanLocal {

    public void create(PrmsPostQualification prmsPostQualification);

    public void update(PrmsPostQualification prmsPostQualification);

    public PrmsPostQualification getPQNos();

//    public PrmsPostQualification getReqPQN(Integer postId);

    public List<PrmsPostQualification> searchByPostQ(PrmsPostQualification prmsPostQualification);

    public List<PrmsPostQualification> searchAllPostQualification();
   
    public List<PrmsFinancialEvalResult> listOfFinEvlNO();

    public List<PrmsSupplyProfile> getItemNameLists(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsFinancialEvlResultyDtl> getBidNameLists(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl,int nominatedRank);
    
    public List<PrmsFinancialEvlResultyDtl> getListofNextSupplier(PrmsFinancialEvalResult prmsFinancialEvalResult,int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl);
     
    public List<PrmsFinancialEvlResultyDtl> getListOfLots(PrmsFinancialEvalResult prmsFinancialEvalResult,int nominatedRank, PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl);
    
    public List<PrmsBidDetail> getLotNames(PrmsBid prmsBid);

    public List<MmsItemRegistration> getItemCodeList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl);

    public List<PrmsFinancialEvlResultyDtl> getLotNumberList(PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl);

    public List<PrmsPostQualification> getPostRqLists();

    public List<PrmsFinancialEvlResultyDtl> getResultFormLists();

    public List<PrmsPostQualification> getParamNameList();

   
   
}
