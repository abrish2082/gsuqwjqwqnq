/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsIsivReceivedFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsIsivReceivedBean implements MmsIsivReceivedBeanLocal {

    @EJB
    MmsIsivReceivedFacade isivReceviedFacade;

    @Override
    public void create(MmsIsivReceived isivReceived) {
        isivReceviedFacade.create(isivReceived);
    }

    @Override
    public void edit(MmsIsivReceived isivReceived) {
        isivReceviedFacade.edit(isivReceived);
    }

    @Override
    public List<MmsIsivReceived> searchByStoreId(MmsIsivReceived isivReceived) {
        return isivReceviedFacade.searchByStoreId(isivReceived);
    }

    @Override
    public List<MmsIsivReceived> searchByStoreIdAndProcessedBy(MmsIsivReceived isivReceived) {
        return isivReceviedFacade.searchByStoreIdAndProcessedBy(isivReceived);
    }

    @Override
    public List<MmsIsivReceived> SearchByStoreAndIsivNo(MmsStoreInformation storeEntity, MmsIsivReceived isiv) {
        return isivReceviedFacade.SearchByStoreAndIsivNo(storeEntity, isiv);
    }

    @Override
    public List<MmsIsivReceived> SearchByStoreAndIsivNoAndProcessedBy(MmsStoreInformation storeEntity, MmsIsivReceived isiv) {
        return isivReceviedFacade.SearchByStoreAndIsivNoAndProcessedBy(storeEntity, isiv);
    }

    @Override
    public MmsIsivReceived getLastTransferNo() {
        return isivReceviedFacade.getLastTransferNo();
    }

    @Override
    public ArrayList<MmsIsivReceived> approvedIsivList(MmsStoreInformation store, int Status) {
        return isivReceviedFacade.getapprovedISIVRecievableList(store, Status);
    }

    @Override
    public MmsIsivReceived getByReceivingId(MmsIsivReceived ivEntity) {
        return isivReceviedFacade.getByReceivingId(ivEntity);
    }

    @Override
    public List<MmsIsivReceived> findByStatus(int Stataus) {
        return isivReceviedFacade.findByStatus(Stataus);
    }

    @Override
    public List<MmsIsivReceived> searchAllByPreparerId(MmsIsivReceived ivReceivedEntity) {
        return isivReceviedFacade.searchAllByPreparerId(ivReceivedEntity);
    }

    @Override
    public List<String> getMmsReceivedColumnNameList() {
        return isivReceviedFacade.getMmsReceivedColumnNameList();
    }

    @Override
    public List<MmsIsivReceived> getReceivedListsByParameter(MmsIsivReceived ivReceivedEntity) {
        return isivReceviedFacade.getReceivedListsByParameter(ivReceivedEntity);
    }

    @Override
    public List<MmsIsivReceived> getReceivedListsByParameter(ColumnNameResolver columnNameResolver, MmsIsivReceived ivReceivedEntity, String columnValue) {
         return isivReceviedFacade.getReceivedListsByParameter(columnNameResolver, ivReceivedEntity, columnValue);
    }

}
