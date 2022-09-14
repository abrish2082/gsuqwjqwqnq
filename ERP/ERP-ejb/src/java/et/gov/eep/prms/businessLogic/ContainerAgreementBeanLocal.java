/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsLuWareHouse;
import et.gov.eep.prms.entity.PrmsContainerAgreement;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.util.List;
import javax.ejb.Local; 


@Local
public interface ContainerAgreementBeanLocal {

    public List<PrmsContainerAgreement> SearchContainer(PrmsContainerAgreement prmsContainerAgreement);

    public PrmsContainerAgreement getLastFormNo();

    public void create(PrmsContainerAgreement prmsContainerAgreement);

    public void update(PrmsContainerAgreement prmsContainerAgreement);

    public PrmsContainerAgreement getSelectedRequest(String formNo);

    public List<PrmsContainerAgreement> SearchByFormNum(PrmsContainerAgreement prmsContainerAgreement);

    public List<PrmsContainerAgreement> searchByFormAndRevisionNum(PrmsContainerAgreement prmsContainerAgreement);

    public List<PrmsContainerAgreement> findAll();

    public List<PrmsContainerAgreement> searchByFormAndRevisionNumber(PrmsContainerAgreement prmsContainerAgreement);

    public List<PrmsContainerAgreement> findByParametereRevesion(PrmsContainerAgreement prmsContainerAgreement);

    public PrmsPortEntry portNameUpdate(PrmsPortEntry papmsPortEntry);

    public List<PrmsPortEntry> portTo(PrmsPortEntry portFrom);
    public List<MmsLuWareHouse> storeLocationLists();

    public MmsLuWareHouse changeWareHouses(MmsLuWareHouse mmsLuWareHouse);

    public List<PrmsGoodsEntrance> getgoodsEntranceNoLists(int approvedStatus);

    public List<PrmsContainerAgreement> getcontAgreeReqLists();
    
    public List<PrmsContainerAgreement> getContainerListsByParameter(PrmsContainerAgreement prmsContainerAgreement);

    public List<ColumnNameResolver> getColumnNameList();


}
