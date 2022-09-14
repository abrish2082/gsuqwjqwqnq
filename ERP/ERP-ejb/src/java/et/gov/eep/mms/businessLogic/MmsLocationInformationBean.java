/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLocationInformation;
import et.gov.eep.mms.mapper.MmsLocationInformationFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsLocationInformationBean implements MmsLocationInformationBeanLocal {

    @EJB
    MmsLocationInformationFacade locInfoFacade;

    /**
     *
     * @param locationInformation
     */
    @Override
    public void create(MmsLocationInformation locationInformation) {
        locInfoFacade.create(locationInformation);
    }

    /**
     *
     * @param locationInformation
     */
    @Override
    public void edit(MmsLocationInformation locationInformation) {
        locInfoFacade.edit(locationInformation);
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    @Override
    public ArrayList<MmsLocationInformation> searchStoreAndShelfInfo(MmsLocationInformation locationInformation) {
      return locInfoFacade.searchStoreAndShelfInfo(locationInformation);
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    @Override
    public MmsLocationInformation getMmsLocationInformation(MmsLocationInformation locationInformation) {
       return locInfoFacade.getMmsLocationInformation(locationInformation);
    }

    /**
     *
     * @param locationInformation
     * @return
     */
    @Override
    public boolean getMmsLocationInformationDup(MmsLocationInformation locationInformation) {
        return locInfoFacade.getMmsLocationInformationDup(locationInformation);
    }

   
}
