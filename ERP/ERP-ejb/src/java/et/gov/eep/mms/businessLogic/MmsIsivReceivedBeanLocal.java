/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsIsiv;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsIsivReceived;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsIsivReceivedBeanLocal {

    public void create(MmsIsivReceived isivReceived);

    public void edit(MmsIsivReceived isivReceived);

    public List<MmsIsivReceived> searchByStoreId(MmsIsivReceived isivReceived);

    public List<MmsIsivReceived> SearchByStoreAndIsivNo(MmsStoreInformation storeEntity, MmsIsivReceived isiv);

    public MmsIsivReceived getLastTransferNo();

    public ArrayList<MmsIsivReceived> approvedIsivList(MmsStoreInformation store, int Status);

    public MmsIsivReceived getByReceivingId(MmsIsivReceived ivEntity);

    public List<MmsIsivReceived> searchByStoreIdAndProcessedBy(MmsIsivReceived isivReceived);

    public List<MmsIsivReceived> SearchByStoreAndIsivNoAndProcessedBy(MmsStoreInformation storeEntity, MmsIsivReceived isiv);

    public List<MmsIsivReceived> findByStatus(int Stataus);

    public List<MmsIsivReceived> searchAllByPreparerId(MmsIsivReceived ivReceivedEntity);

    public List<String> getMmsReceivedColumnNameList();

    public List<MmsIsivReceived> getReceivedListsByParameter(MmsIsivReceived ivReceivedEntity);

    public List<MmsIsivReceived> getReceivedListsByParameter(ColumnNameResolver columnNameResolver, MmsIsivReceived ivReceivedEntity, String columnValue);

  

}
