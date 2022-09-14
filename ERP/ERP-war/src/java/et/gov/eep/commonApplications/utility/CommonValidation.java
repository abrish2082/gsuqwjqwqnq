/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Administrator
 */
public class CommonValidation {

    /**
     * The method compares the starting and ending dates to make sure that the
     * starting date is always smaller than the ending date.
     * @param startDate the start date
     * @param endDate the end date
     * @return true if startDate is smaller than endDate, false otherwise.
     */
    public boolean validateDateSequence(String startDate, String endDate) {
        boolean flag = true;        // assume the everything is alright
        String todayInEc = StringDateManipulation.toDayInEc();
        if (StringDateManipulation.compareDate(startDate, endDate) > 0 && StringDateManipulation.compareDate(startDate, todayInEc) > 0) {
            flag = false;
        }
        return flag;
    }

  public static boolean isUpdatePhase(ValueChangeEvent event){
 		PhaseId phaseId = event.getPhaseId();
 		boolean ret=false;
 		if(phaseId.equals(PhaseId.ANY_PHASE)){
 			event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
 			event.queue();
 			ret=false;
 		}else if(phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)){
 			ret=true;
 		}
 		return ret;
 	}

}
