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
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.mapper.PrmsContainerAgreementFacade;
import et.gov.eep.mms.mapper.MmsStoreInformationFacade;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ContainerAgreementBean implements ContainerAgreementBeanLocal {

    @EJB
    PrmsContainerAgreementFacade prmsContainerAgreementFacade;
    @EJB
    MmsStoreInformationFacade mmsStoreInformationFacade;

    @Override
    public List<PrmsContainerAgreement> SearchContainer(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.SearchContainer(prmsContainerAgreement);
    }

    @Override
    public PrmsContainerAgreement getLastFormNo() {
        return prmsContainerAgreementFacade.getLastFormNo();
    }

    @Override
    public void create(PrmsContainerAgreement prmsContainerAgreement) {
        prmsContainerAgreementFacade.create(prmsContainerAgreement);
    }

    @Override
    public void update(PrmsContainerAgreement prmsContainerAgreement) {
        prmsContainerAgreementFacade.edit(prmsContainerAgreement);
    }

    @Override
    public PrmsContainerAgreement getSelectedRequest(String formNo) {
        return prmsContainerAgreementFacade.getFormNumber(formNo);
    }

    @Override
    public List<PrmsContainerAgreement> SearchByFormNum(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.SearchByFormNum(prmsContainerAgreement);
    }

    @Override
    public List<PrmsContainerAgreement> searchByFormAndRevisionNum(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.searchByFormAndRevisionNum(prmsContainerAgreement);
    }

    @Override
    public List<PrmsContainerAgreement> findAll() {
        return prmsContainerAgreementFacade.findAll();
    }

    @Override
    public List<PrmsContainerAgreement> searchByFormAndRevisionNumber(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.searchByFormAndRevisionNumber(prmsContainerAgreement);
    }

    @Override
    public List<PrmsContainerAgreement> findByParametereRevesion(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.findByParametereRevesion(prmsContainerAgreement);
    }

    @Override
    public PrmsPortEntry portNameUpdate(PrmsPortEntry papmsPortEntry) {
        return prmsContainerAgreementFacade.portUpdate(papmsPortEntry);
    }

    @Override
    public List<PrmsPortEntry> portTo(PrmsPortEntry portFrom) {
        return prmsContainerAgreementFacade.portTo(portFrom);
    }

    @Override
    public List<MmsLuWareHouse> storeLocationLists() {
        return prmsContainerAgreementFacade.listStoreLocations();
    }

    @Override
    public MmsLuWareHouse changeWareHouses(MmsLuWareHouse mmsLuWareHouse) {
        return prmsContainerAgreementFacade.changeWareHouses(mmsLuWareHouse);
    }

    @Override
    public List<PrmsGoodsEntrance> getgoodsEntranceNoLists(int approvedStatus) {
        return prmsContainerAgreementFacade.goodEntranceLists(approvedStatus);
    }

    @Override
    public List<PrmsContainerAgreement> getcontAgreeReqLists() {
        return prmsContainerAgreementFacade.getcontAgreeReqLists();
    }
     @Override
    public List<PrmsContainerAgreement> getContainerListsByParameter(PrmsContainerAgreement prmsContainerAgreement) {
        return prmsContainerAgreementFacade.getContainerListsByParameter(prmsContainerAgreement);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return prmsContainerAgreementFacade.getColumnNameList();
    }

}
